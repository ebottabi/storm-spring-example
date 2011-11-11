package mykidong.storm.dao.mongo.support;



public class QueryMng {

    private Query query;
    private Field field;
    private Sort sort;
    private Integer limit;
    private Integer skip;

    public QueryMng(){
    }

    public QueryMng(Integer pageNum, Integer limit){
        int skip = 0;
        if (pageNum > 1) {
            skip = (pageNum-1) * limit;
        }
        this.skip = skip;
        this.limit = limit;
    }


    public Query getQuery() {
        return query;
    }

    public QueryMng setQuery(Query query) {
        this.query = query;
        return this;
    }

    public static QueryMng query(Query query) {
        QueryMng queryMng = new QueryMng();
        queryMng.setQuery(query);
        return queryMng;
    }

    public static QueryMng query(Object queryObj) {
        QueryMng queryMng = new QueryMng();
        queryMng.setQuery(new Query(queryObj));
        return queryMng;
    }

    public Field getField() {
        return field;
    }
    public QueryMng setField(Field field) {
        this.field = field;
        return this;
    }

    public static QueryMng field(Field field) {
        QueryMng queryMng = new QueryMng();
        queryMng.setField(field);
        return queryMng;
    }

    public Sort getSort() {
        return this.sort;
    }

    public QueryMng setSort(Sort sort) {
        this.sort = sort;
        return this;
    }

    public static QueryMng sort(Sort sort) {
        QueryMng queryMng = new QueryMng();
        queryMng.setSort(sort);
        return queryMng;
    }

    public Integer getLimit() {
        return limit;
    }

    public QueryMng setLimit(Integer limit) {
        this.limit = limit;
        return this;
    }

    public static QueryMng limit(Integer limit) {
        QueryMng queryMng = new QueryMng();
        queryMng.setLimit(limit);
        return queryMng;
    }

    public Integer getSkip() {
        return skip;
    }

    public QueryMng setSkip(Integer skip) {
        this.skip = skip;
        return this;
    }

    public static QueryMng skip(Integer skip) {
        QueryMng queryMng = new QueryMng();
        queryMng.setSkip(skip);
        return queryMng;
    }

    public static QueryMng page(Integer pageNum, Integer limit){
        return new QueryMng(pageNum, limit);
    }

    public QueryMng setPageNum(Integer pageNum) {
        int skip = 0;
        if (pageNum > 1) {
            skip = (pageNum-1) * limit;
        }

        this.skip = skip;
        return this;
    }
}
