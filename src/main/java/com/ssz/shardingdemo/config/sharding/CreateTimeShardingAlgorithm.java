package com.ssz.shardingdemo.config.sharding;


import org.apache.commons.lang3.StringUtils;
import org.apache.shardingsphere.driver.jdbc.core.datasource.ShardingSphereDataSource;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ssz
 *
 * 跟据创建时间分表的接口
 */

public interface CreateTimeShardingAlgorithm {

	/**
	 * 将日期转换为分表的后缀格式
	 * @param date
	 * @return
	 */
	String buildNodesSuffix(Integer date);

	/**
	 * 分表的阶梯方式，物理表每次递增规则,返回date的后一天或一个月或一年
	 * @param date
	 * @return
	 */
	Integer buildNodesAfterDate(Integer date);

	/**
	 * 分表的阶梯方式，物理表每次递减规则,返回date的前一天或一个月或一年
	 * @param date
	 * @return
	 */
	Integer buildNodesBeforeDate(Integer date);

	/**
	 * 构建可用表的nodes
	 * @param tableName 表名
	 * @param year 最初的表，用来计算物理表数
	 * @return 物理表的集合，用，号拼接
	 */
	String buildNodes(String tableName, Integer year);

	default void createTables(ShardingSphereDataSource dataSource, String tableName, Integer count) {

		try (Connection connection = dataSource.getConnection(); Statement statement = connection.createStatement()) {

			LocalDate today = LocalDate.now();
			Integer year = today.getYear();
			String oldTableName = "";
			String newTableName = "";
			for (int i = 0; i < count; i++) {
				oldTableName = tableName + "_" + buildNodesSuffix(year);
				year = buildNodesAfterDate(year);
				newTableName = tableName + "_" + buildNodesSuffix(year);
				statement.execute("create table IF NOT EXISTS `" + newTableName + "` like  `" + oldTableName + "`");
			}
		}
		catch (SQLException throwables) {
			throw new RuntimeException("建表失败");
		}

	}

}
