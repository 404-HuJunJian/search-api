package com.watering.service.impl;

import com.watering.dao.*;
import com.watering.domain.DTO.ResponseDTO;
import com.watering.domain.VO.*;
import com.watering.domain.entity.*;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;

@Service
public class CareerServiceImpl {
    @Autowired
    private CareerEntityMapper careerEntityMapper;

    @Autowired
    private EnterpriseEntityMapper enterpriseEntityMapper;

    @Autowired
    private DepartmentEntityMapper departmentEntityMapper;

    @Autowired
    private OccupationEntityMapper occupationEntityMapper;

    @Autowired
    private CrimeEntityMapper crimeEntityMapper;

    @Autowired
    private ManagerEntityMapper managerEntityMapper;

    @Autowired
    private AttendanceEntityMapper attendanceEntityMapper;

    @Autowired
    private PerformanceEntityMapper performanceEntityMapper;


    public ResponseDTO<List<CareerVO>> findAllCareer(Integer empid) {
        List<CareerEntity> careerEntities = careerEntityMapper.selectAllByEmpid(empid);
        ArrayList<CareerVO> list = new ArrayList<>();
        for (CareerEntity careerEntity : careerEntities) {
            CareerVO careerVO = new CareerVO();
            BeanUtils.copyProperties(careerEntity, careerVO);
            EnterpriseEntity enterpriseEntity = enterpriseEntityMapper.selectByPrimaryKey(careerEntity.getEntid());
            DepartmentEntity departmentEntity = departmentEntityMapper.selectByPrimaryKey(careerEntity.getDepid());
            OccupationEntity occupationEntity = occupationEntityMapper.selectByPrimaryKey(careerEntity.getOccid());
            careerVO.setEnterprise(enterpriseEntity.getName());
            careerVO.setDepartment(departmentEntity.getName());
            careerVO.setOccupation(occupationEntity.getName());
            list.add(careerVO);
        }
        return ResponseDTO.succData(list);
    }


    public ResponseDTO<AvgScoreVO> findAvgScore(Integer empid) {
        float attitude = 0, ability = 0;
        AvgScoreVO avgScoreVO = new AvgScoreVO();
        List<CareerEntity> careerEntities = careerEntityMapper.selectAllByEmpid(empid);
        for (CareerEntity careerEntity : careerEntities) {
            attitude += careerEntity.getAttendance();
            ability += careerEntity.getPerformance();
        }
        avgScoreVO.setAttitude(attitude / careerEntities.size());
        avgScoreVO.setAbility(ability / careerEntities.size());
        return ResponseDTO.succData(avgScoreVO);
    }

    public ResponseDTO<List<CrimeVO>> findAllCrime(Integer empid) {
        List<CrimeVO> list = new ArrayList<>();
        List<CrimeEntity> crimeEntities = crimeEntityMapper.selectAllByEmpid(empid);
        for (CrimeEntity crimeEntity : crimeEntities) {
            CrimeVO crimeVO = new CrimeVO();
            BeanUtils.copyProperties(crimeEntity, crimeVO);
            crimeVO.setManager(managerEntityMapper.selectByPrimaryKey(crimeEntity.getManid()).getName());
            list.add(crimeVO);
        }
        return ResponseDTO.succData(list);
    }

    public ResponseDTO<List<AttendanceVO>> findCurAttendance(Integer carid) {
        List<AttendanceVO> list = new ArrayList<>();
        List<AttendanceEntity> attendanceEntities = attendanceEntityMapper.selectAllByCarid(carid);
        for (AttendanceEntity attendanceEntity : attendanceEntities) {
            AttendanceVO attendanceVO = new AttendanceVO();
            ManagerEntity managerEntity = managerEntityMapper.selectByPrimaryKey(attendanceEntity.getManid());
            BeanUtils.copyProperties(attendanceEntity, attendanceVO);
            attendanceVO.setManager(managerEntity.getName());
            list.add(attendanceVO);

        }
        return ResponseDTO.succData(list);
    }

    public ResponseDTO<List<PerformanceVO>> findCurPerformance(Integer carid) {
        List<PerformanceVO> list = new ArrayList<>();
        List<PerformanceEntity> performanceEntities = performanceEntityMapper.selectAllByCarid(carid);
        for (PerformanceEntity performanceEntity : performanceEntities) {
            PerformanceVO performanceVO = new PerformanceVO();
            ManagerEntity managerEntity = managerEntityMapper.selectByPrimaryKey(performanceEntity.getManid());
            BeanUtils.copyProperties(performanceEntity, performanceVO);
            performanceVO.setManager(managerEntity.getName());
            list.add(performanceVO);
        }
        return ResponseDTO.succData(list);
    }

    public ResponseDTO<List<CrimeVO>> findCurCrime(Integer carid) {
        List<CrimeVO> list = new ArrayList<>();
        List<CrimeEntity> crimeEntities = crimeEntityMapper.selectAllByCarid(carid);
        for (CrimeEntity crimeEntity : crimeEntities) {
            CrimeVO crimeVO = new CrimeVO();
            ManagerEntity managerEntity = managerEntityMapper.selectByPrimaryKey(crimeEntity.getManid());
            BeanUtils.copyProperties(crimeEntity, crimeVO);
            crimeVO.setManager(managerEntity.getName());
            list.add(crimeVO);
        }
        return ResponseDTO.succData(list);
    }
}
