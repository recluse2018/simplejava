package run.simplejava.util.sql;

import org.apache.commons.lang3.StringUtils;

/**
 ** 查询Sql拼接
 * 
 * @author Jiangshaode
 *
 */
public class QuerySql {
	String[] tables;
	String[] fields;
	String[] sorts;
	Sort sort;
	StringBuilder ExpressBuilder = new StringBuilder();
	int Num = 0;

	public QuerySql(String... DataTables) {
		this.tables = DataTables;
	}

	/**
	 ** 添加字段，字符串数组，或者多个字符串
	 * 
	 * @param Args
	 */
	public void SetField(String... Args) {
		this.fields = Args;
	}

	/***
	 ** 添加条件（表达式）,每次添加一个条件，不用写and
	 * 
	 * @param Express
	 */
	public void AddCondition(String Express) {
		if (Num > 0) {
			ExpressBuilder.append(" and ");
		}
		ExpressBuilder.append(Express);
		Num++;
	}

	/**
	 ** 添加排序功能 EnumLists.Sort
	 * 
	 * @param Field
	 * @param rank
	 */
	public void SetSortField(String... Field) {
		this.sorts = Field;
	}

	public void SetSortMethod(Sort rank) {
		this.sort = rank;
	}

	/**
	 ** 返回SQL拼接字符串
	 */
	public String toString() {
		String expree=this.ExpressBuilder.toString();
		if(StringUtils.isNotEmpty(expree)) {
			expree=" where "+expree;
		}
		String order="";
		if(org.apache.commons.lang3.ArrayUtils.isNotEmpty(this.sorts)) {
			order=" order by "+org.apache.commons.lang3.StringUtils.join(this.sorts,",")+" ";
		}
		
		return "select "+StringUtils.join(this.fields,",")+" from "+StringUtils.join(this.tables,",")+" "+expree+" "+order+" "+this.sort;
	}
}
