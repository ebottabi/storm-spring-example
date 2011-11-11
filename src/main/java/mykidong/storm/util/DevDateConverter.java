package mykidong.storm.util;

import java.util.Date;

import org.apache.commons.beanutils.Converter;


public class DevDateConverter implements Converter{

    private final String[] acceptableFormats;

    public DevDateConverter(){
        this.acceptableFormats = new String[]{
                "yyyyMMdd",
                "yyyyMMddHHmmss",
                "yyyy-MM-dd",
                "yyyy-MM-dd HH:mm:ss",
                "yyyy/MM/dd",
                "yyyy/MM/dd HH:mm:ss",
                "yyyy.MM.dd",
                "yyyy.MM.dd HH:mm:ss"
                };
    }

    public Object convert(Class paramClass, Object paramObject){
        if(paramObject instanceof Date) return paramObject;
        if(paramObject == null) throw new DevBaseException("value is null");
        String strParam = (String)paramObject;
        for(String fmt :  acceptableFormats){
            if(DateUtils.isValidDate(strParam, fmt)) {
                return DateUtils.parse(strParam, fmt);
            }
        }
        if(true) throw new DevBaseException("값을 변환 하는 과정에서 오류가 발생했습니다.");
        return null;
    }
}
