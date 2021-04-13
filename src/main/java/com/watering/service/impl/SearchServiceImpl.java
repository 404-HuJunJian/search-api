package com.watering.service.impl;

import com.watering.dao.DepartmentEntityMapper;
import com.watering.dao.EnterpriseEntityMapper;
import com.watering.dao.HrEntityMapper;
import com.watering.dao.ManagerEntityMapper;
import com.watering.domain.DTO.ResponseDTO;
import com.watering.domain.VO.HrVO;
import com.watering.domain.VO.ManagerVO;
import com.watering.domain.entity.DepartmentEntity;
import com.watering.domain.entity.EnterpriseEntity;
import com.watering.domain.entity.HrEntity;
import com.watering.domain.entity.ManagerEntity;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SearchServiceImpl {
    @Autowired
    private HrEntityMapper hrEntityMapper;

    @Autowired
    private EnterpriseEntityMapper enterpriseEntityMapper;

    @Autowired
    private ManagerEntityMapper managerEntityMapper;

    @Autowired
    private DepartmentEntityMapper departmentEntityMapper;

    public ResponseDTO<List<HrVO>> hrSearch() {
        List<HrVO> list = new ArrayList<>();
        List<HrEntity> hrEntities = hrEntityMapper.selectAll();
        for (HrEntity hrEntity : hrEntities) {
            HrVO hrVO = new HrVO();
            EnterpriseEntity enterpriseEntity = enterpriseEntityMapper.selectByPrimaryKey(hrEntity.getEntid());
            BeanUtils.copyProperties(hrEntity, hrVO);
            hrVO.setEnterprise(enterpriseEntity.getName());
            list.add(hrVO);
        }
        return ResponseDTO.succData(list);
    }

    public ResponseDTO<List<ManagerVO>> managerSearch() {
        List<ManagerVO> list = new ArrayList<>();
        List<ManagerEntity> managerEntities = managerEntityMapper.selectAll();
        for (ManagerEntity managerEntity : managerEntities) {
            ManagerVO managerVO = new ManagerVO();
            EnterpriseEntity enterpriseEntity = enterpriseEntityMapper.selectByPrimaryKey(managerEntity.getEntid());
            DepartmentEntity departmentEntity = departmentEntityMapper.selectByPrimaryKey(managerEntity.getDepid());
            BeanUtils.copyProperties(managerEntity, managerVO);
            managerVO.setEnterprise(enterpriseEntity.getName());
            managerVO.setDepartment(departmentEntity.getName());
            list.add(managerVO);
        }
        return ResponseDTO.succData(list);
    }

    public ResponseDTO<List<DepartmentEntity>> departmentSearch(){
        List<DepartmentEntity> list = departmentEntityMapper.selectAll();
        return ResponseDTO.succData(list);
    }
}

