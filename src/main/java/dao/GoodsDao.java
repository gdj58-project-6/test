package dao;

import java.sql.Connection;



import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

import vo.Goods;

public class GoodsDao {
	
	// RemoveGoods
	public int removeGoods(Connection conn, Goods goods) throws Exception {
		int row = 0;
		String sql = "DELETE FROM goods WHERE goods_code = ?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, goods.getGoodsCode());
		row = stmt.executeUpdate();		
		
		stmt.close();
		return row;
	}
	
	
	// ModifyGoodsForm 
	public ArrayList<HashMap<String, Object>> modifyGoodsForm(Connection conn, int goodsCode) throws Exception {
		ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		String sql = "SELECT" 
					+ " g.goods_code goodsCode"
					+ ", g.goods_name goodsName"
					+ ", g.goods_price goodsPrice"
					+ ", g.goods_category goodsCategory"
					+ ", g.goods_memo goodsMemo"
					+ ", g.soldout soldout"
					+ ", g.emp_id empId"
					+ ", g.hit hit"
					+ ", gi.filename fileName"
					+ ", gi.origin_name originName"
					+ ", gi.content_type contentType"
					+ " FROM goods g "
					+ " INNER JOIN goods_img gi "
					+ " ON g.goods_code = gi.goods_code "
					+ " WHERE g.goods_code = ?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, goodsCode);
		ResultSet rs = stmt.executeQuery();
		while(rs.next()) {
			HashMap<String, Object> m = new HashMap<String, Object>();
			m.put("goodsCode", rs.getInt("goodsCode"));
			m.put("goodsName", rs.getString("goodsName"));
			m.put("goodsPrice", rs.getString("goodsPrice"));
			m.put("goodsCategory", rs.getString("goodsCategory"));
			m.put("goodsMemo", rs.getString("goodsMemo"));
			m.put("soldout", rs.getString("soldout"));
			m.put("empId", rs.getString("empId"));
			m.put("hit", rs.getInt("hit"));
			m.put("fileName", rs.getString("fileName"));
			m.put("originName", rs.getString("originName"));
			m.put("contentType", rs.getString("contentType"));
			list.add(m);
		}
		rs.close();
		stmt.close();
		
		return list;
	}
	
	// ModifyGoodsAction 
	public int modifyGoodsAction(Connection conn, Goods goods) throws Exception {	
		int row = 0;
		String sql = "UPDATE goods SET goods_name=?, goods_price=?, goods_category =?, goods_memo=?, soldout=?, hit=? WHERE goods_code=?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setString(1, goods.getGoodsName());
		stmt.setInt(2, goods.getGoodsPrice());
		stmt.setString(3, goods.getGoodsCategory());
		stmt.setString(4, goods.getGoodsMemo());
		stmt.setString(5, goods.getSoldout());
		stmt.setInt(6, goods.getHit());
		stmt.setInt(7, goods.getGoodsCode());
		
		row = stmt.executeUpdate();
		
		stmt.close();
		return row;
	}
		
	
	// GoodsOne
	public ArrayList<HashMap<String, Object>> goodsOne(Connection conn, int goodsCode) throws Exception {
		ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		String sql = "SELECT"
					+ " g.goods_code goodsCode"
					+ ", g.goods_name goodsName"
					+ ", g.goods_price goodsPrice"
					+ ", g.goods_memo goodsMemo"
					+ ", gi.filename fileName"
					+ " FROM goods g INNER JOIN goods_img gi "
					+ " ON g.goods_code = gi.goods_code "
					+ " WHERE g.goods_code = ?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, goodsCode);
		ResultSet rs = stmt.executeQuery();
		while(rs.next()) {
			HashMap<String, Object> m = new HashMap<String, Object>();
			m.put("goodsCode", rs.getInt("goodsCode"));
			m.put("goodsName", rs.getString("goodsName"));
			m.put("goodsPrice", rs.getString("goodsPrice"));
			m.put("goodsMemo", rs.getString("goodsMemo"));
			m.put("fileName", rs.getString("fileName"));
			list.add(m);
		}
		rs.close();
		stmt.close();
		
		return list;
	}
	// 상품 전체 수 구하기
	public int goodsCount(Connection conn, String word) throws Exception {
		int cnt = 0;
		String sql = null;
		PreparedStatement stmt = null;
		if(word == null || word.equals("")) {
			sql = "SELECT COUNT(*) cnt FROM goods";
			stmt = conn.prepareStatement(sql);
		} else {
			sql = "SELECT COUNT(*) cnt FROM goods WHERE goods_name LIKE ?";
			stmt = conn.prepareStatement(sql);
			stmt.setString(1, "%"+word+"%");
			
		}
		
		ResultSet rs = stmt.executeQuery();
		
		if(rs.next()) {
			cnt = rs.getInt("cnt");
		}
		rs.close();
		stmt.close();

		return cnt;
	}
	
	
	// GoodsList 검색기능 추가해야 됨.. 
	public ArrayList<HashMap<String, Object>> selectGoodsList(Connection conn, int beginRow, int rowPerPage, String word, String sort, String category) throws Exception {
		ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();		
		PreparedStatement stmt = null;
		String sql = null;
		// 검색어 null이나 공백이 넘어오면 공백처리
		if(word == null || word.equals("")) {
			word = "";
		}
		if(sort == null || sort.equals("")) {
			sort = "";
		}
		if(category == null || category.equals("")) {
			category = "";
		}
		// 정렬 createdate 
		if(sort.equals("createdate")) {
			// 정렬 O 카테고리 X
			if(category.equals("")) {
				// 정렬 O 카테고리 X 검색 X
				if(word.equals("")) {
					// 전체 GoodsList 신상품순 정렬
					sql = "SELECT"
							+ " g.goods_code goodsCode"
							+ ", g.goods_name goodsName"
							+ ", g.goods_price goodsPrice"
							+ ", g.goods_category goodsCategory"
							+ ", g.goods_memo goodsMemo"
							+ ", g.hit hit"
							+ ", g.createdate createdate"
							+ ", gi.filename fileName"
							+ " FROM goods g INNER JOIN goods_img gi "
							+ " ON g.goods_code = gi.goods_code "
							+ " ORDER BY g.createdate DESC "
							+ " LIMIT ?, ?";
					stmt = conn.prepareStatement(sql);
					stmt.setInt(1, beginRow);
					stmt.setInt(2, rowPerPage);
				} else { // 정렬 createdate 카테고리 X 검색 O
					sql = "SELECT"
							+ " g.goods_code goodsCode"
							+ ", g.goods_name goodsName"
							+ ", g.goods_price goodsPrice"
							+ ", g.goods_category goodsCategory"
							+ ", g.goods_memo goodsMemo"
							+ ", g.hit hit"
							+ ", g.createdate createdate"
							+ ", gi.filename fileName"
							+ " FROM goods g INNER JOIN goods_img gi "
							+ " ON g.goods_code = gi.goods_code "
							+ " WHERE goods_name LIKE ? "
							+ " ORDER BY g.createdate DESC "
							+ " LIMIT ?, ?";
					stmt = conn.prepareStatement(sql);
					stmt.setString(1, "%"+word+"%");
					stmt.setInt(2, beginRow);
					stmt.setInt(3, rowPerPage);
				}
			} else { // 정렬 createdate 카테고리 O 검색 X
				if(word.equals("")) {
					sql = "SELECT"
							+ " g.goods_code goodsCode"
							+ ", g.goods_name goodsName"
							+ ", g.goods_price goodsPrice"
							+ ", g.goods_category goodsCategory"
							+ ", g.goods_memo goodsMemo"
							+ ", g.hit hit"
							+ ", g.createdate createdate"
							+ ", gi.filename fileName"
							+ " FROM goods g INNER JOIN goods_img gi "
							+ " ON g.goods_code = gi.goods_code "
							+ " WHERE goods_category = ? "
							+ " ORDER BY g.createdate DESC "
							+ " LIMIT ?, ?";
					stmt = conn.prepareStatement(sql);
					stmt.setString(1, category);
					stmt.setInt(2, beginRow);
					stmt.setInt(3, rowPerPage);
				} else { // 정렬 createdate 카테고리 O 검색 O
					sql = "SELECT"
							+ " g.goods_code goodsCode"
							+ ", g.goods_name goodsName"
							+ ", g.goods_price goodsPrice"
							+ ", g.goods_category goodsCategory"
							+ ", g.goods_memo goodsMemo"
							+ ", g.hit hit"
							+ ", g.createdate createdate"
							+ ", gi.filename fileName"
							+ " FROM goods g INNER JOIN goods_img gi "
							+ " ON g.goods_code = gi.goods_code "
							+ " WHERE goods_category = ? AND goods_name LIKE ? "
							+ " ORDER BY g.createdate DESC "
							+ " LIMIT ?, ?";
					stmt = conn.prepareStatement(sql);
					stmt.setString(1, category);
					stmt.setString(2, "%"+word+"%");
					stmt.setInt(3, beginRow);
					stmt.setInt(4, rowPerPage);
				}
			}
		// 정렬 hit 카테고리 X 검색 X
		} else if(sort.equals("hit")) {
			if(category.equals("")) {
				if(word.equals("")) {
					sql = "SELECT"
							+ " g.goods_code goodsCode"
							+ ", g.goods_name goodsName"
							+ ", g.goods_price goodsPrice"
							+ ", g.goods_category goodsCategory"
							+ ", g.goods_memo goodsMemo"
							+ ", g.hit hit"
							+ ", g.createdate createdate"
							+ ", gi.filename fileName"
							+ " FROM goods g INNER JOIN goods_img gi "
							+ " ON g.goods_code = gi.goods_code "
							+ " ORDER BY g.hit DESC "
							+ " LIMIT ?, ?";
					stmt = conn.prepareStatement(sql);
					stmt.setInt(1, beginRow);
					stmt.setInt(2, rowPerPage);
				} else { // 정렬 hit 카테고리 X 검색 O
					sql = "SELECT"
							+ " g.goods_code goodsCode"
							+ ", g.goods_name goodsName"
							+ ", g.goods_price goodsPrice"
							+ ", g.goods_category goodsCategory"
							+ ", g.goods_memo goodsMemo"
							+ ", g.hit hit"
							+ ", g.createdate createdate"
							+ ", gi.filename fileName"
							+ " FROM goods g INNER JOIN goods_img gi "
							+ " ON g.goods_code = gi.goods_code "
							+ " WHERE goods_name LIKE ? "
							+ " ORDER BY g.hit DESC "
							+ " LIMIT ?, ?";
					stmt = conn.prepareStatement(sql);
					stmt.setString(1, "%"+word+"%");
					stmt.setInt(2, beginRow);
					stmt.setInt(3, rowPerPage);
				}
			} else { // 정렬 hit 카테고리 O 검색 X
				if(word.equals("")) {
					sql = "SELECT"
							+ " g.goods_code goodsCode"
							+ ", g.goods_name goodsName"
							+ ", g.goods_price goodsPrice"
							+ ", g.goods_category goodsCategory"
							+ ", g.goods_memo goodsMemo"
							+ ", g.hit hit"
							+ ", g.createdate createdate"
							+ ", gi.filename fileName"
							+ " FROM goods g INNER JOIN goods_img gi "
							+ " ON g.goods_code = gi.goods_code "
							+ " WHERE goods_category = ? "
							+ " ORDER BY g.hit DESC "
							+ " LIMIT ?, ?";
					stmt = conn.prepareStatement(sql);
					stmt.setString(1, category);
					stmt.setInt(2, beginRow);
					stmt.setInt(3, rowPerPage);
				} else { // 정렬 hit 카테고리 O 검색 O
					sql = "SELECT"
							+ " g.goods_code goodsCode"
							+ ", g.goods_name goodsName"
							+ ", g.goods_price goodsPrice"
							+ ", g.goods_category goodsCategory"
							+ ", g.goods_memo goodsMemo"
							+ ", g.hit hit"
							+ ", g.createdate createdate"
							+ ", gi.filename fileName"
							+ " FROM goods g INNER JOIN goods_img gi "
							+ " ON g.goods_code = gi.goods_code "
							+ " WHERE goods_category = ? AND goods_name LIKE ? "
							+ " ORDER BY g.hit DESC "
							+ " LIMIT ?, ?";
					stmt = conn.prepareStatement(sql);
					stmt.setString(1, category);
					stmt.setString(2, "%"+word+"%");
					stmt.setInt(3, beginRow);
					stmt.setInt(4, rowPerPage);
				}
			}
		}
		
		ResultSet rs = stmt.executeQuery();
		while(rs.next()) {
			HashMap<String, Object> m = new HashMap<String, Object>();
			m.put("goodsCode", rs.getInt("goodsCode"));
			m.put("goodsName", rs.getString("goodsName"));
			m.put("goodsPrice", rs.getString("goodsPrice"));
			m.put("goodsCategory", rs.getString("goodsCategory"));
			m.put("goodsMemo", rs.getString("goodsMemo"));
			m.put("hit", rs.getInt("hit"));
			m.put("createdate", rs.getString("createdate"));
			m.put("fileName", rs.getString("fileName"));
			list.add(m);
		}
		rs.close();
		stmt.close();
		
		return list;
	}
	
	
	// AddGoods
	public HashMap<String, Integer> addGoods(Connection conn, Goods goods) throws Exception {
		String sql ="INSERT INTO goods(goods_name, goods_price, goods_category, goods_memo, soldout, emp_id, hit, createdate) VALUES(?, ?, ?, ?, ?, ?, ?, NOW())";
		PreparedStatement stmt = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
		// PreparedStatement.RETURN_GENERATED_KEYS 쿼리실행 후 생성된 auto_increment값을 ResultSet에 반환
		stmt.setString(1, goods.getGoodsName());
		stmt.setInt(2, goods.getGoodsPrice());
		stmt.setString(3, goods.getGoodsCategory());
		stmt.setString(4, goods.getGoodsMemo());
		stmt.setString(5, goods.getSoldout());
		stmt.setString(6, goods.getEmpId());
		stmt.setInt(7, goods.getHit());
		
		int row = stmt.executeUpdate();
		ResultSet rs = stmt.getGeneratedKeys();
		int autoKey = 0;
		if(rs.next()) {
			autoKey = rs.getInt(1);
		}
		
		HashMap<String, Integer> map = new HashMap<String, Integer>();
		map.put("row", row);
		map.put("autoKey", autoKey);
		
		rs.close();
		stmt.close();
		return map;
	}
	
	// 바로구매시 보여줄 굿즈 상세
	public HashMap<String, Object> goodsOneByOrder(Connection conn, Goods goods) throws Exception {
		HashMap<String, Object> goodsOne = null;
		ResultSet rs = null;
		String sql = "SELECT "
					+ "g.goods_code goodsCode "
					+ ", gi.filename filename "
					+ ", g.goods_name goodsName "
					+ ", g.goods_price goodsPrice "
					+ "FROM goods g INNER JOIN goods_img gi "
					+ "ON g.goods_code = gi.goods_code "
					+ "WHERE g.goods_code = ?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, goods.getGoodsCode());
		
		rs = stmt.executeQuery();
		
		if(rs.next()) {
			goodsOne = new HashMap<String, Object>();
			goodsOne.put("goodsCode", rs.getInt("goodsCode"));
			goodsOne.put("filename", rs.getString("filename"));
			goodsOne.put("goodsName", rs.getString("goodsName"));
			goodsOne.put("goodsPrice", rs.getInt("goodsPrice"));
		}
		
		rs.close();
		stmt.close();
		
		return goodsOne;
		
	}
		
}
