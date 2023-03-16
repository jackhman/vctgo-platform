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
import com.vctgo.job.domain.XxlJobGroup;
import com.vctgo.job.service.IXxlJobGroupService;
import com.vctgo.common.core.web.controller.BaseController;
import com.vctgo.common.core.web.domain.AjaxResult;
import com.vctgo.common.core.utils.poi.ExcelUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 定时任务分组Controller
 *
 * @author vctgo
 * @date 2023-01-01
 */
@RestController
@RequestMapping("/group")
public class XxlJobGroupController extends BaseController
{
    @Autowired
    private IXxlJobGroupService xxlJobGroupService;

    /**
     * 查询定时任务分组列表
     */
    @RequiresPermissions("job:group:list")
    @GetMapping("/list")
    public AjaxResult list(XxlJobGroup xxlJobGroup)
    {
        IPage<XxlJobGroup> list = xxlJobGroupService.selectXxlJobGroupPage(xxlJobGroup);
        return AjaxResult.success(list);
    }

    /**
     * 导出定时任务分组列表
     */
    @RequiresPermissions("job:group:export")
    @Log(title = "定时任务分组", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, XxlJobGroup xxlJobGroup)
    {
        List<XxlJobGroup> list = xxlJobGroupService.selectXxlJobGroupList(xxlJobGroup);
        ExcelUtil<XxlJobGroup> util = new ExcelUtil<XxlJobGroup>(XxlJobGroup.class);
        util.exportExcel(response, list, "定时任务分组数据");
    }

    /**
     * 获取定时任务分组详细信息
     */
    @RequiresPermissions("job:group:query")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(xxlJobGroupService.getById(id));
    }

    /**
     * 新增定时任务分组
     */
    @RequiresPermissions("job:group:add")
    @Log(title = "定时任务分组", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody XxlJobGroup xxlJobGroup)
    {
        return toAjax(xxlJobGroupService.insertXxlJobGroup(xxlJobGroup));
    }

    /**
     * 修改定时任务分组
     */
    @RequiresPermissions("job:group:edit")
    @Log(title = "定时任务分组", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody XxlJobGroup xxlJobGroup)
    {
        return toAjax(xxlJobGroupService.updateXxlJobGroup(xxlJobGroup));
    }

    /**
     * 删除定时任务分组
     */
    @RequiresPermissions("job:group:remove")
    @Log(title = "定时任务分组", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(xxlJobGroupService.deleteXxlJobGroupByIds(ids));
    }

    @Log(title = "定时任务列表下拉选", businessType = BusinessType.DELETE)
    @GetMapping("/getSelectAll")
    public AjaxResult getSelectAll(XxlJobGroup xxlJobGroup) {
        return AjaxResult.success(xxlJobGroupService.selectXxlJobGroupList(xxlJobGroup));
    }

}
