package com.wanda.epc.mapper;

import com.wanda.epc.dto.ElectricDataDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;


/**
 * @description: 变压器mapper
 * @author: liurs
 * @create: 2022-11-08 15:36
 **/
@Mapper
public interface ElectricDataMapper {

    /**
     * 变压器参数
     *
     * @return
     */
    List<ElectricDataDto> queryElectricData(@Param("tableName") String tableName, @Param("queryTime") String queryTime);

    /**
     * 变压器能耗
     *
     * @return
     */
    List<ElectricDataDto> queryMonthData(@Param("tableName") String tableName, @Param("queryTime") String queryTime);

}
