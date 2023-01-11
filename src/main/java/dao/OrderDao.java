package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;

import vo.Customer;
import vo.Orders;

public class OrderDao {
	// 주문하기
	public int insertOrderByCustomer(Connection conn, Orders orders) throws Exception {
		int row = 0;
		String sql = "INSERT INTO orders(customer_id, address_code, order_price, order_state, order_memo, createdate) VALUES(?, ?, ?, '결제', ?, NOW())";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setString(1, orders.getCustomerId());
		stmt.setInt(2, orders.getAddressCode());
		stmt.setInt(3, orders.getOrderPrice());
		stmt.setString(4, orders.getOrderMemo());
		
		row = stmt.executeUpdate();
		
		stmt.close();
		
		return row;
	}
	
	// 관리자용 모든 주문 리스트
	public ArrayList<HashMap<String, Object>> selectAllOrderList(Connection conn) throws Exception {
		ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		ResultSet rs = null;
		String sql = "SELECT "
					+ "o.order_code orderCode"
					+ ", gi.filename filename "
					+ ", g.goods_name goodsName "
					+ ", t.cnt cnt "
					+ ", o.customer_id customerId "
					+ ", o.order_price orderPrice "
					+ ", o.order_state orderState "
					+ ", o.createdate createdate "
					+ "FROM orders o "
					+ "INNER JOIN (SELECT order_code orderCode, goods_code goodsCode, (COUNT(*)-1) cnt FROM order_goods GROUP BY order_code) t "
					+ "ON o.order_code = t.orderCode "
					+ "INNER JOIN goods g "
					+ "ON t.goodsCode = g.goods_code "
					+ "INNER JOIN goods_img gi "
					+ "ON g.goods_code = gi.goods_code "
					+ "GROUP BY o.order_code";
		PreparedStatement stmt = conn.prepareStatement(sql);
		
		rs = stmt.executeQuery();
		
		while(rs.next()) {
			HashMap<String, Object> m = new HashMap<String, Object>();
			m.put("orderCode", rs.getInt("orderCode"));
			m.put("filename", rs.getString("filename"));
			m.put("goodsName", rs.getString("goodsName"));
			m.put("cnt", rs.getInt("cnt"));
			m.put("customerId", rs.getString("customerId"));
			m.put("orderPrice", rs.getInt("orderPrice"));
			m.put("orderState", rs.getString("orderState"));
			m.put("createdate", rs.getString("createdate"));
			list.add(m);
		}
		
		rs.close();
		stmt.close();
		
		return list;
	}
	
	// 고객용 주문 리스트
	public ArrayList<HashMap<String, Object>> selectOrderByCustomerList(Connection conn, Customer customer) throws Exception {
		ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		ResultSet rs = null;
		String sql = "SELECT "
					+ "o.order_code orderCode"
					+ ", gi.filename filename "
					+ ", g.goods_name goodsName "
					+ ", t.cnt cnt "
					+ ", o.order_price orderPrice "
					+ ", o.order_state orderState "
					+ ", o.createdate createdate "
					+ "FROM orders o "
					+ "INNER JOIN (SELECT order_code orderCode, goods_code goodsCode, (COUNT(*)-1) cnt FROM order_goods GROUP BY order_code) t "
					+ "ON o.order_code = t.orderCode "
					+ "INNER JOIN goods g "
					+ "ON t.goodsCode = g.goods_code "
					+ "INNER JOIN goods_img gi "
					+ "ON g.goods_code = gi.goods_code "
					+ "WHERE o.customer_id = ? "
					+ "GROUP BY o.order_code";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setString(1, customer.getCustomerId());
		
		rs = stmt.executeQuery();
		
		while(rs.next()) {
			HashMap<String, Object> m = new HashMap<String, Object>();
			m.put("orderCode", rs.getInt("orderCode"));
			m.put("filename", rs.getString("filename"));
			m.put("goodsName", rs.getString("goodsName"));
			m.put("cnt", rs.getInt("cnt"));
			m.put("orderPrice", rs.getInt("orderPrice"));
			m.put("orderState", rs.getString("orderState"));
			m.put("createdate", rs.getString("createdate"));
			list.add(m);
		}
		
		rs.close();
		stmt.close();
		
		return list;
	}
	
	// orderOne
	// orderCode에 대한 주문자 정보 출력
	public HashMap<String, Object> selectCustomerByOrder(Connection conn, int orderCode) throws Exception {
		HashMap<String, Object> customerByOrder = null;
		ResultSet rs = null;
		String sql = "SELECT "
					+ "o.order_code orderCode "
					+ ", c.customer_id customerId "
					+ ", c.customer_name customerName "
					+ ", c.customer_phone customerPhone "
					+ ", ca.address address "
					+ ", o.order_price orderPrice "
					+ ", o.order_state orderState "
					+ ", o.order_memo orderMemo "
					+ ", o.createdate createdtae "
					+ "FROM customer c "
					+ "INNER JOIN customer_address ca ON c.customer_id = ca.customer_id "
					+ "INNER JOIN orders o ON o.customer_id = c.customer_id "
					+ "WHERE o.order_code = ?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, orderCode);
		
		rs = stmt.executeQuery();
		
		if(rs.next()) {
			customerByOrder = new HashMap<String, Object>();
			customerByOrder.put("orderCode", rs.getInt("orderCode"));
			customerByOrder.put("customerId", rs.getString("customerId"));
			customerByOrder.put("customerName", rs.getString("customerName"));
			customerByOrder.put("customerPhone", rs.getString("customerPhone"));
			customerByOrder.put("address", rs.getString("address"));
			customerByOrder.put("orderPrice", rs.getInt("orderPrice"));
			customerByOrder.put("orderState", rs.getString("orderState"));
			customerByOrder.put("orderMemo", rs.getString("orderMemo"));
			customerByOrder.put("createdtae", rs.getString("createdtae"));
		}
		
		rs.close();
		stmt.close();
		
		return customerByOrder;
	}
	
	// orderCode에 대한 구매 굿즈 출력
	public ArrayList<HashMap<String, Object>> selectOrderByGoodsList(Connection conn, int orderCode) throws Exception {
		ArrayList<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		ResultSet rs = null;
		String sql = "SELECT"
					+ "	og.goods_code goodsCode "
					+ ", gi.filename filename"
					+ ", g.goods_name goodsName"
					+ ", og.order_goods_price orderGoodsPrice"
					+ ", og.order_goods_quantity orderGoodsQuantity "
					+ "FROM orders o INNER JOIN order_goods og "
					+ "ON o.order_code = og.order_code "
					+ "INNER JOIN goods g "
					+ "ON og.goods_code = g.goods_code "
					+ "INNER JOIN goods_img gi "
					+ "ON g.goods_code = gi.goods_code "
					+ "WHERE o.order_code = ?";
		PreparedStatement stmt = conn.prepareStatement(sql);
		stmt.setInt(1, orderCode);
		
		rs = stmt.executeQuery();
		
		while(rs.next()) {
			HashMap<String, Object> m = new HashMap<String, Object>();
			m.put("goodsCode", rs.getInt("goodsCode"));
			m.put("filename", rs.getString("filename"));
			m.put("goodsName", rs.getString("goodsName"));
			m.put("orderGoodsPrice", rs.getInt("orderGoodsPrice"));
			m.put("orderGoodsQuantity", rs.getInt("orderGoodsQuantity"));
			list.add(m);
		}
		
		rs.close();
		stmt.close();
		
		return list;
	}
}
