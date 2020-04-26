package run.simplejava.util.sql;

import org.apache.commons.lang3.StringUtils;

/**
 **分页SQL字符串拼接
 * @author Jiangshaode
 *
 */
public class PagingQueryUtil {
	String[] tables;
	String[] fields;
	String[] beforeSortFields;//分类前排序字段
	String[] afterSortFields;//分类后排序字段
	int current;//当前页面
	int countNum;//一页显示多少条记录
	Sort rankSort;//排序方法
   private StringBuilder ExpressSb=new StringBuilder();//条件表达式
   /**
    ** 分页SQL字符串拼接
    */
   public PagingQueryUtil() {

   }
   /***
    ** 设置您要查询的数据库的表号，可以是一个，或者多个关联查询，如果查询的字段在各表中有重复，请给各表设置别名，如 table1 t1,table2 t2
    * @param DataTables
    */
   public void SetTable(String...DataTables) {
	   this.tables=DataTables;
   }
   int Num=0;
   /**
    ** 添加字段，字符串数组，或者多个字符串
    * @param Args
    */
   public void SetField(String...Args) {
	   this.fields=Args;	   
   }
   /**
    ** 设置分类前的排序字段
    * @param BeforeSortField
    */
   public void SetBeforeSortFields(String...BeforeSortField) {
	   this.beforeSortFields=BeforeSortField;
   }
   /**
    ** 设置分类后页面需要的排序字段
    * @param AfterSortField
    */
   public void SetAfterSortFields(String...AfterSortField) {
	   this.afterSortFields=AfterSortField;
   }
   /***
    ** 设置排序方法，ASC or Desc
    * @param Rank
    */
   public void SetSort(Sort Rank) {
	   this.rankSort=Rank;
   }
   /***
    ** 设置当前页面
    * @param CurrentPage
    */
   public void SetCurrentPage(int CurrentPage) {
	   this.current=CurrentPage;
   }
   /**
    ** 设置一页显示多少条记录
    * @param Count
    */
   public void SetPageCountNumForCurrent(int Count) {
	   this.countNum=Count;
   }
   /***
    ** 添加条件（表达式）,每次添加一个条件，不用写and
    * @param Express
    */
   public void AddCondition(String Express) {
  	 if(Num>0) {
  		ExpressSb.append(" and ");
  	 }   	
  	 ExpressSb.append(Express);
  	 Num++;
   }
   /***
    ** 返回计算结果，以字符串格式返回。
    * @return
    */
   public String toString() {
	   StringBuilder dataSqlBuilder=new StringBuilder();
	   dataSqlBuilder.append("SELECT ROWINDEX,");
	   dataSqlBuilder.append(StringUtils.join(this.fields,","));
	   dataSqlBuilder.append(" FROM (SELECT ROW_NUMBER() OVER (ORDER BY");
	   dataSqlBuilder.append(" ");
	   dataSqlBuilder.append(StringUtils.join(this.beforeSortFields,","));
	   dataSqlBuilder.append(" ");
	   dataSqlBuilder.append(this.rankSort);
	   dataSqlBuilder.append(")AS ROWINDEX,");
	   dataSqlBuilder.append(StringUtils.join(fields,","));
	   dataSqlBuilder.append(" FROM ");
	   dataSqlBuilder.append(StringUtils.join(tables,","));
	   String expree=this.ExpressSb.toString();
	   if(StringUtils.isNotEmpty(expree)) {
		   dataSqlBuilder.append(" WHERE ");
		   dataSqlBuilder.append(expree);
	   }
	   dataSqlBuilder.append(")T WHERE T.ROWINDEX BETWEEN ((");
	   dataSqlBuilder.append(this.current);
	   dataSqlBuilder.append("-1)*");
	   dataSqlBuilder.append(this.countNum);
	   dataSqlBuilder.append("+1) and (");
	   dataSqlBuilder.append(this.countNum);
	   dataSqlBuilder.append("*");
	   dataSqlBuilder.append(this.current);
	   dataSqlBuilder.append(") ORDER BY ");
	   dataSqlBuilder.append(StringUtils.join(this.afterSortFields,","));
	   dataSqlBuilder.append(" ");
	   dataSqlBuilder.append(this.rankSort);
	   return dataSqlBuilder.toString();
   }

}
