package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

import vo.Goods;

public class GoodsDao {
	// AddGoodsList
	public ArrayList<HashMap<String, Object>> selectGoodsList(Connection conn) throws Exception {
		ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		String sql = "SELECT";
		
		return list;
	}
	
	
	// AddGoods
	public HashMap<String, Integer> addGoods(Connection conn, Goods goods) throws Exception {
		String sql ="INSERT INTO goods(goods_name, goods_price, soldout, emp_id, hit, createdate) VALUES(?, ?, ?, ?, ?, NOW())";
		PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
		// PreparedStatement.RETURN_GENERATED_KEYS 쿼리실행 후 생성된 auto_increment값을 ResultSet에 반환
		stmt.setString(1, goods.getGoodsName());
		stmt.setInt(2, goods.getGoodsPrice());
		stmt.setString(3, goods.getSoldout());
		stmt.setString(4, goods.getEmpId());
		stmt.setInt(5, goods.getHit());
		
		int row = stmt.executeUpdate();
		ResultSet rs = stmt.getGeneratedKeys();
		int autoKey = 0;
		if(rs.next()) {
			autoKey = rs.getInt(1);
		}
		
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		map.put("row", row);
		map.put("autoKey", autoKey);
		return map;
	}
		
}
