package run.simplejava.util.sql;

import java.util.Map;
import java.util.Map.Entry;

public class AndOrUpdateSql {
	/**
	 * 如果记录存在，则更新记录，否则插入记录。
	 * @param table  数据库中的表
	 * @param expression  查询表达式，比如id=5
	 * @param Setexpression  更新数据表达式，比如 set name='china'
	 * @param keys  插入数据字段，插入数据的值
	 * @param values
	 * @return
	 */
	  public static String InsertOrUpdate(String table,String expression,String Setexpression,String keys,String values) {
	    	return	 "if exists(select * from "+table+" where "+expression+") update "+table+" "+Setexpression+"  else  insert into "+table+"("+keys+") values("+values+")";
	    	
	  }
	  /***
	   ** 拼接插入、新增数据
	   * @param tableName 数据库表的名称
	   * @param map  字段值
	   * @return 完整数据Sql
	   * @throws Exception 如果map is null 报错
	   */
	  public static String InSertDataSql(String tableName,Map<String, Object> map) throws Exception {
		  StringBuilder Values=new StringBuilder(")values(");
		  StringBuilder SqlStr=new StringBuilder("insert into "+tableName+"(");
		  int count=map.size();
		  if(count>0) {
			  int len=1;
			  for (Entry<String, Object> iterable_element : map.entrySet()) {
				  SqlStr.append(iterable_element.getKey());
				  Values.append(iterable_element.getValue());
				  if(len<count) {
					  SqlStr.append(",");
					  Values.append(",");
				  }
				  len++;
				  
			  }
			  SqlStr.append(Values);
			  return SqlStr.toString();
		  }else {
			  throw new Exception("Map is Null");
		  }
		  
	  }
      
}
