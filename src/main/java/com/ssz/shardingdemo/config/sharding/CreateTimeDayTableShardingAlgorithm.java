package com.ssz.shardingdemo.config.sharding;

import cn.hutool.core.date.DateUtil;
import com.google.common.collect.Range;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.shardingsphere.sharding.api.sharding.standard.PreciseShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.RangeShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.StandardShardingAlgorithm;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * 按年分表实现
 * @author ssz
 */
@Slf4j
public class CreateTimeDayTableShardingAlgorithm
		implements StandardShardingAlgorithm<Timestamp>, CreateTimeShardingAlgorithm {

	private static final String FORMAT_LINK_DAY = "yyyy";

	@Override
	public Collection<String> doSharding(Collection<String> availableTargetNames,
			RangeShardingValue<Timestamp> shardingValue) {
		Range<Timestamp> valueRange;
		valueRange = shardingValue.getValueRange();
		LocalDateTime start = null;
		try {
			start = valueRange.lowerEndpoint().toLocalDateTime();
		}
		catch (Exception e) {
			start = LocalDateTime.now().minusDays(15L);
		}
		LocalDateTime end = null;
		try {
			end = valueRange.upperEndpoint().toLocalDateTime();
		}
		catch (Exception e) {
			end = LocalDateTime.now();
		}
		Set<String> suffixList = new HashSet<>();

		Iterator<String> iterator = availableTargetNames.iterator();
		String tableName = iterator.next();
		String name = tableName.substring(0, tableName.lastIndexOf("_"));
		if (start != null && end != null) {
			String startName = DateUtil.format(start, FORMAT_LINK_DAY);
			String endName = DateUtil.format(end, FORMAT_LINK_DAY);
			while (!startName.equals(endName)) {
				if (availableTargetNames.contains(name + "_" + startName)) {
					suffixList.add(name + "_" + startName);
				}
				start = start.plusDays(1L);
				startName = DateUtil.format(start, FORMAT_LINK_DAY);

			}
			if (availableTargetNames.contains(name + "_" + endName)) {
				suffixList.add(name + "_" + endName);
			}
		}
		if (CollectionUtils.isNotEmpty(suffixList)) {
			return suffixList;
		}

		return availableTargetNames;
	}

	@Override
	public Properties getProps() {
		return null;
	}

	@Override
	public void init(Properties properties) {
		// 空
	}

	@Override
	public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<Timestamp> shardingValue) {
		LocalDateTime time = shardingValue.getValue().toLocalDateTime();
		DateTimeFormatter dtf2 = DateTimeFormatter.ofPattern(FORMAT_LINK_DAY);
		String format = dtf2.format(time);
		for (String str : availableTargetNames) {
			if (str.endsWith(format)) {
				return str;
			}
		}
		return null;
	}

	@Override
	public String buildNodesSuffix(Integer date) {
		return date.toString();
	}

	@Override
	public Integer buildNodesBeforeDate(Integer date) {
		return date-1;
	}

	@Override
	public Integer buildNodesAfterDate(Integer date) {
		return date+1;
	}

}