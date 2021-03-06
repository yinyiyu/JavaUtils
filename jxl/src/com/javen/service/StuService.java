package com.javen.service;

import java.io.File;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jxl.Sheet;
import jxl.Workbook;

import com.javen.db.DBhelper;
import com.javen.entity.StuEntity;

public class StuService {
	/**
	 * 查询stu表中所有的数据
	 * 
	 * @return
	 */
	public static List<StuEntity> getAllByDb() {
		List<StuEntity> list = new ArrayList<StuEntity>();
		try {
			DBhelper db = new DBhelper();
			String sql = "select * from stu";
			ResultSet rs = db.Search(sql, null);
			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String sex = rs.getString("sex");
				int num = rs.getInt("num");

				// System.out.println(id+" "+name+" "+sex+ " "+num);
				list.add(new StuEntity(id, name, sex, num));
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 查询指定目录中电子表格中所有的数据
	 * 
	 * @param file
	 *            文件完整路径
	 * @return
	 */
	public static List<StuEntity> getAllByExcel(String file) {
		List<StuEntity> list = new ArrayList<StuEntity>();
		try {
			Workbook rwb = Workbook.getWorkbook(new File(file));
			Sheet rs = rwb.getSheet("Test Shee 1");// 或者rwb.getSheet(0)
			int clos = rs.getColumns();// 得到所有的列
			int rows = rs.getRows();// 得到所有的行

			System.out.println(clos + " rows:" + rows);
			for (int i = 1; i < rows; i++) {
				for (int j = 0; j < clos; j++) {
					// 第一个是列数，第二个是行数
					String id = rs.getCell(j++, i).getContents();// 默认最左边编号也算一列
																	// 所以这里得j++
					String name = rs.getCell(j++, i).getContents();
					String sex = rs.getCell(j++, i).getContents();
					String num = rs.getCell(j++, i).getContents();

					System.out.println("id:" + id + " name:" + name + " sex:"
							+ sex + " num:" + num);
					list.add(new StuEntity(Integer.parseInt(id), name, sex,
							Integer.parseInt(num)));
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;

	}

	/**
	 * 通过Id判断是否存在
	 * 
	 * @param id
	 * @return
	 */
	public static boolean isExist(int id) {
		try {
			DBhelper db = new DBhelper();
			ResultSet rs = db.Search("select * from stu where id=?",
					new String[] { id + "" });
			if (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public static void main(String[] args) {
		/*
		 * List<StuEntity> all=getAllByDb(); for (StuEntity stuEntity : all) {
		 * System.out.println(stuEntity.toString()); }
		 */
		List<StuEntity> lists = new ArrayList<StuEntity>();
		lists = new StuService().getAllByDb();
		for (StuEntity stuEntity : lists) {
			System.out.println(stuEntity.toString());
		}
		System.out.println(isExist(1));
	}
}
