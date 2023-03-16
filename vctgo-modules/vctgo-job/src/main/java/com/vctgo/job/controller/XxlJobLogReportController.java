package com.vctgo.job.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.vctgo.common.log.annotation.Log;
import com.vctgo.common.log.enums.BusinessType;
import com.vctgo.common.security.annotation.RequiresPermissions;
import com.vctgo.job.domain.XxlJobLogReport;
import com.vctgo.job.service.IXxlJobLogReportService;
import com.vctgo.common.core.web.controller.BaseController;
import com.vctgo.common.core.web.domain.AjaxResult;
import com.vctgo.common.core.utils.poi.ExcelUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 报告Controller
 *
 * @author vctgo
 * @date 2023-01-03
 */
@RestController
@RequestMapping("/report")
public class XxlJobLogReportController extends BaseController
{
    @Autowired
    private IXxlJobLogReportService xxlJobLogReportService;

    /**
     * 查询报告列表
     */
    @RequiresPermissions("job:report:list")
    @GetMapping("/list")
    public AjaxResult list(XxlJobLogReport xxlJobLogReport)
    {
        IPage<XxlJobLogReport> list = xxlJobLogReportService.selectXxlJobLogReportPage(xxlJobLogReport);
        return AjaxResult.success(list);
    }

    /**
     * 导出报告列表
     */
    @RequiresPermissions("job:report:export")
    @Log(title = "报告", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, XxlJobLogReport xxlJobLogReport)
    {
        List<XxlJobLogReport> list = xxlJobLogReportService.selectXxlJobLogReportList(xxlJobLogReport);
        ExcelUtil<XxlJobLogReport> util = new ExcelUtil<XxlJobLogReport>(XxlJobLogReport.class);
        util.exportExcel(response, list, "报告数据");
    }

    /**
     * 获取报告详细信息
     */
    @RequiresPermissions("job:report:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(xxlJobLogReportService.selectXxlJobLogReportById(id));
    }

    /**
     * 新增报告
     */
    @RequiresPermissions("job:report:add")
    @Log(title = "报告", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody XxlJobLogReport xxlJobLogReport)
    {
        return toAjax(xxlJobLogReportService.insertXxlJobLogReport(xxlJobLogReport));
    }

    /**
     * 修改报告
     */
    @RequiresPermissions("job:report:edit")
    @Log(title = "报告", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody XxlJobLogReport xxlJobLogReport)
    {
        return toAjax(xxlJobLogReportService.updateXxlJobLogReport(xxlJobLogReport));
    }

    /**
     * 删除报告
     */
    @RequiresPermissions("job:report:remove")
    @Log(title = "报告", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(xxlJobLogReportService.deleteXxlJobLogReportByIds(ids));
    }
}
