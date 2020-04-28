package com.zhang.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.zhang.health.constant.MessageConstant;
import com.zhang.health.entity.Result;
import com.zhang.health.service.ReportService;
import com.zhang.health.utils.DateUtils;
import com.zhang.health.utils.QueryResultUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

/**
 * @author zhang
 * @version 1.0
 * @date 2020/4/28 13:13
 */
@RestController
@RequestMapping("/report")
public class ReportController {

    @Reference
    private ReportService reportService;

    /**
     * 获取过去12个月对应的会员数量
     *
     * @return Result
     */
    @GetMapping("/getMemberReport")
    public Result getMemberReport() {
        Map<String, Object> map = reportService.getMemberReport();
        return QueryResultUtils.checkQueryMapResult(map);
    }

    /**
     * 套餐占比
     *
     * @return Result
     */
    @GetMapping("/getSetMealReport")
    public Result getSetMealReport() {
        Map<String, Object> map = reportService.getSetMealReport();
        return QueryResultUtils.checkQueryMapResult(map);
    }

    /**
     * 根据页面需要查询相关运营数据
     *
     * @return Result
     */
    @GetMapping("/getBusinessReportData")
    public Result getBusinessReportData() {
        Map<String, Object> map = reportService.getBusinessReportData();
        return QueryResultUtils.checkQueryMapResult(map);
    }

    /**
     * 导出报表数据
     */
    @GetMapping("/exportBusinessReport")
    public void exportBusinessReport(HttpServletRequest request, HttpServletResponse response) {
        try {
            //获取数据
            Map<String, Object> result = reportService.getBusinessReportData();

            //取出返回结果数据，准备将报表数据写入到Excel文件中
            String reportDate = (String) result.get("reportDate");
            Integer todayNewMember = (Integer) result.get("todayNewMember");
            Integer totalMember = (Integer) result.get("totalMember");
            Integer thisWeekNewMember = (Integer) result.get("thisWeekNewMember");
            Integer thisMonthNewMember = (Integer) result.get("thisMonthNewMember");
            Integer todayOrderNumber = (Integer) result.get("todayOrderNumber");
            Integer thisWeekOrderNumber = (Integer) result.get("thisWeekOrderNumber");
            Integer thisMonthOrderNumber = (Integer) result.get("thisMonthOrderNumber");
            Integer todayVisitsNumber = (Integer) result.get("todayVisitsNumber");
            Integer thisWeekVisitsNumber = (Integer) result.get("thisWeekVisitsNumber");
            Integer thisMonthVisitsNumber = (Integer) result.get("thisMonthVisitsNumber");
            List<Map<String, Object>> hotSetmeal = (List<Map<String, Object>>) result.get("hotSetmeal");

            //动态获取模板路径
            String filePath
                    = request.getSession().getServletContext().getRealPath("templates") + File.separator + "report_template.xlsx";
            XSSFWorkbook xssfWorkbook = new XSSFWorkbook(new FileInputStream(new File(filePath)));
            XSSFSheet sheet = xssfWorkbook.getSheetAt(0);
            //读取第一个工作表
            XSSFRow row = sheet.getRow(2);
            //日期
            row.getCell(5).setCellValue(reportDate);

            row = sheet.getRow(4);
            //新增会员数（本日）
            row.getCell(5).setCellValue(todayNewMember);
            //总会员数
            row.getCell(7).setCellValue(totalMember);

            row = sheet.getRow(5);
            //本周新增会员数
            row.getCell(5).setCellValue(thisWeekNewMember);
            //本月新增会员数
            row.getCell(7).setCellValue(thisMonthNewMember);

            row = sheet.getRow(7);
            //今日预约数
            row.getCell(5).setCellValue(todayOrderNumber);
            //今日到诊数
            row.getCell(7).setCellValue(todayVisitsNumber);

            row = sheet.getRow(8);
            //本周预约数
            row.getCell(5).setCellValue(thisWeekOrderNumber);
            //本周到诊数
            row.getCell(7).setCellValue(thisWeekVisitsNumber);

            row = sheet.getRow(9);
            //本月预约数
            row.getCell(5).setCellValue(thisMonthOrderNumber);
            //本月到诊数
            row.getCell(7).setCellValue(thisMonthVisitsNumber);

            int index = 12;
            if (hotSetmeal != null && hotSetmeal.size() > 0) {
                for (Map<String, Object> map : hotSetmeal) {
                    String name = (String) map.get("name");
                    String remark = (String) map.get("remark");
                    Long setmeal_count = (Long) map.get("setmeal_count");
                    BigDecimal proportion = (BigDecimal) map.get("proportion");
                    XSSFRow rowSetMeal = sheet.getRow(index++);
                    rowSetMeal.getCell(4).setCellValue(name);
                    rowSetMeal.getCell(5).setCellValue(setmeal_count);
                    rowSetMeal.getCell(6).setCellValue(proportion.doubleValue());
                    //这个单元格自动换行
                    XSSFCell cell7 = rowSetMeal.getCell(7);
                    cell7.getCellStyle().setWrapText(true);
                    cell7.setCellValue(remark);
                }
            }

            //设置两头一流
            ServletOutputStream outputStream = response.getOutputStream();
            //代表的是Excel文件类型
            response.setContentType("application/vnd.ms-excel");
            //指定以附件形式进行下载
            response.setHeader("content-Disposition", "attachment;filename=report.xlsx");
            xssfWorkbook.write(outputStream);

            outputStream.flush();
            outputStream.close();
            xssfWorkbook.close();
        } catch (IOException e) {
            throw new RuntimeException("操作异常");
        }
    }
}
