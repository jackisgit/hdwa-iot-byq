package com.wanda.epc;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSON;
import com.wanda.epc.device.BaseDevice;
import com.wanda.epc.device.CommonDevice;
import com.wanda.epc.dto.ElectricDataDto;
import com.wanda.epc.mapper.ElectricDataMapper;
import com.wanda.epc.param.DeviceMessage;
import com.wanda.epc.util.ConvertUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @program: iot_epc
 * @description: 博锐尚格 变压器数据采集
 * @author: liurs
 * @create: 2022-11-08 10:09
 **/
@Slf4j
@Service
public class ElectricEpcDevice extends BaseDevice {

    @Value("${epc.tableId}")
    private String tableId;

    @Autowired
    private ElectricDataMapper electricDataMapper;


    @Autowired
    CommonDevice commonDevice;

    @Override
    public void sendMessage(DeviceMessage dm) {


        //如果数据变化则，发送emqx

        if (dm != null) {
            commonDevice.sendMessage(dm);
        }

    }

    @Override
    public boolean processData() {
        String tableName = "electriccurrentdata_" + tableId;
        String queryTime = DateUtil.format(new Date(), "yyyy-MM-dd HH:00");

        String tableTime = DateUtil.format(new Date(), "yyyyMM");
        String serviceTableName = tableTime + "_monthdata_" + tableId;

        List<ElectricDataDto> electricDataVos = electricDataMapper.queryElectricData(tableName, queryTime);
        // 变压器温度
        electricDataVos.addAll(electricDataMapper.queryMonthData(serviceTableName,queryTime));

        //log.info("采集能源数据为：{}", JSON.toJSONString(electricDataVos));
        for (ElectricDataDto electricData : electricDataVos) {
            String funcid = electricData.getFuncid();
            String sign = electricData.getSign();
            List<DeviceMessage> deviceMessage = deviceParamListMap.get(sign + "_" + funcid);
            if (!CollectionUtils.isEmpty(deviceMessage)) {
                deviceMessage.forEach(device -> {
                    if (Objects.nonNull(device)) {
                        device.setValue(electricData.getData());
                        device.setUpdateTime(ConvertUtil.getNowDateTime("yyyyMMddHHmmss"));
                        log.info("采集到{}变压器的值：{}", sign + "_" + funcid, electricData.getData());
                        sendMessage(device);
                    }
                });
            }
        }
        return true;
    }

    @Override
    public void dispatchCommand(String meter, Integer funcid, String value, String message) {
    }

    @Override
    public boolean processData(String... obj) throws Exception {
        return false;
    }


}
