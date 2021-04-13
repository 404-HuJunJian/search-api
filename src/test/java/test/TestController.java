package test;

import com.watering.ApiMain8081;
import com.watering.constant.FileTypeEnum;
import com.watering.dao.*;
import com.watering.domain.DTO.ResponseDTO;
import com.watering.domain.DTO.search.KeyWord;
import com.watering.domain.VO.*;
import com.watering.domain.entity.*;
import com.watering.domain.DTO.employee.EmployeeUpdateBaseDTO;
import com.watering.domain.DTO.search.KeyWord;
import com.watering.domain.DTO.search.SearchDTO;
import com.watering.domain.DTO.search.SearchFilter;
import com.watering.domain.VO.EmployeeVO;
import com.watering.domain.VO.HrVO;
import com.watering.domain.entity.*;
import com.watering.domain.VO.ManagerVO;
import com.watering.service.FileUploadService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Created with IntelliJ IDEA.
 *
 * @Auther: Parsley
 * @Date: 2021/03/24/17:19
 * @Description:
 */
@SpringBootTest(classes = ApiMain8081.class)
public class TestController {

    @Autowired
    private AttendanceEntityMapper mapper;
    @Autowired
    private CareerEntityMapper careerEntityMapper;
    @Autowired
    private CrimeEntityMapper crimeEntityMapper;
    @Autowired
    private DepartmentEntityMapper departmentEntityMapper;
    @Autowired
    private EmployeeEntityMapper employeeEntityMapper;
    @Autowired
    private EnterpriseEntityMapper enterpriseEntityMapper;
    @Autowired
    private HrEntityMapper hrEntityMapper;
    @Autowired
    private IndustryEntityMapper industryEntityMapper;
    @Autowired
    private ManagerEntityMapper managerEntityMapper;
    @Autowired
    private OccupationEntityMapper occupationEntityMapper;
    @Autowired
    private PerformanceEntityMapper performanceEntityMapper;
    @Autowired
    private ScoreEntityMapper scoreEntityMapper;
    @Autowired
    private FileUploadService fileUploadService;

    @Test
    public void test(){
        List lists = new ArrayList();
        lists.add(mapper.selectAll());
        lists.add(careerEntityMapper.selectAll());
        lists.add(crimeEntityMapper.selectAll());
        lists.add(departmentEntityMapper.selectAll());
        lists.add(employeeEntityMapper.selectAll());
        lists.add(enterpriseEntityMapper.selectAll());
        lists.add(hrEntityMapper.selectAll());
        lists.add(industryEntityMapper.selectAll());
        lists.add(managerEntityMapper.selectAll());
        lists.add(occupationEntityMapper.selectAll());
        lists.add(performanceEntityMapper.selectAll());
        lists.add(scoreEntityMapper.selectAll());
        System.out.println(lists);
    }

    @Test
    public void test1(){

    ManagerEntity managerEntity = new ManagerEntity(null);
    ManagerVO managerVO =new ManagerVO(2,new Date(),null,null,null,null,null);
        BeanUtils.copyProperties(managerEntity,managerVO);
        System.out.println(managerVO);
}

    @Test
    public void test2(){
        System.out.println(UUID.randomUUID().toString().replace("-",""));
        System.out.println( "apk.java".substring("apk.java".lastIndexOf(".")));
        System.out.println( "apk.java".substring(0,"apk.java".lastIndexOf(".")));
    }

    @Test
    public void test3(){
        HrEntity hrEntity = hrEntityMapper.selectByPrimaryKey(1);
        System.out.println(hrEntity);
//        ManagerEntity managerEntity = managerEntityMapper.selectByPrimaryKey(1);
//        System.out.println(managerEntity);
    }

    @Test
    public void test4(){
        System.out.println(new BCryptPasswordEncoder().encode("zhaojing"));
        System.out.println(new BCryptPasswordEncoder().encode("xhjy"));
        System.out.println(new BCryptPasswordEncoder().encode("zhuzhen"));
        System.out.println(new BCryptPasswordEncoder().encode("zhangdudu"));
    }

    public void test5(){
        EmployeeEntity employeeEntity = employeeEntityMapper.selectByPrimaryKey(1);
        String enterpriseName = null;
        String departmentName = null;
        HrVO hrVO = null;
        if(employeeEntity.isHired()){
            hrVO = new HrVO();
            enterpriseName = enterpriseEntityMapper.selectByPrimaryKey(employeeEntity.getEntid()).getName();
            departmentName = departmentEntityMapper.selectByPrimaryKey(employeeEntity.getDepid()).getName();
            HrEntity hrEntity =hrEntityMapper.selectByPrimaryKey(employeeEntity.getHrid());
            BeanUtils.copyProperties(hrEntity,hrVO);
            hrVO.setEnterprise(enterpriseName);
        }
        EmployeeVO employeeVO =new EmployeeVO();
        BeanUtils.copyProperties(employeeEntity,employeeVO);
        employeeVO.setEnterprise(enterpriseName);
        employeeVO.setDepartment(departmentName);
        employeeVO.setHr(hrVO);
        System.out.println(employeeVO);
    }

    @Test
    public void updateEmployeeBasicInfo() throws FileNotFoundException {
        EmployeeUpdateBaseDTO employeeUpdateBaseDTO = new EmployeeUpdateBaseDTO();
        employeeUpdateBaseDTO.setId(14);
        employeeUpdateBaseDTO.setAddress("address_test");
        employeeUpdateBaseDTO.setDegree(-1);
        employeeUpdateBaseDTO.setMail("mail_test");
        employeeUpdateBaseDTO.setMajor("major_test");
        employeeUpdateBaseDTO.setSchool("school_test");
        employeeUpdateBaseDTO.setTel("tel_test");
        String newPhoto = employeeUpdateBaseDTO.getPhoto();
        String newResume = employeeUpdateBaseDTO.getResume();
        EmployeeEntity employeeEntity = employeeEntityMapper.selectByPrimaryKey(employeeUpdateBaseDTO.getId());
        String oldPhoto = employeeEntity.getPhoto();
        String oldResume = employeeEntity.getResume();
        BeanUtils.copyProperties(employeeUpdateBaseDTO,employeeEntity);
        if(null!=newPhoto){
            if(fileUploadService.checkFile(newPhoto, FileTypeEnum.IMG_PHOTO))
                newPhoto = FileTypeEnum.IMG_PHOTO.getUrl() + newPhoto;
            else
                newPhoto = oldPhoto;
        }else{
            newPhoto = oldPhoto;
        }
        if(null!=newResume){
            if(fileUploadService.checkFile(newResume,FileTypeEnum.IMG_RESUME))
                newResume = FileTypeEnum.IMG_RESUME.getUrl() + newResume;
            else
                newResume = oldResume;
        } else{
            newResume = oldResume;
        }
        employeeEntity.setPhoto(newPhoto);
        employeeEntity.setResume(newResume);
        System.out.println(employeeEntity);
    }

    @Test
    public void test6(){
        EmployeeEntity employeeEntity = new EmployeeEntity();
        employeeEntityMapper.insert(employeeEntity);
        Integer id = employeeEntity.getId();
        System.out.println(id);
    }

    @Test
    public void test7(){
        System.out.println(new Date());
    }

    @Test
    public void test8(){
        SearchDTO searchDTO = new SearchDTO();
        KeyWord keyWord = new KeyWord();
        keyWord.setType(KeyWord.Type.DEPARTMENT);
        keyWord.setValue("1,2");
        SearchFilter searchFilter =new SearchFilter();
        searchFilter.setStart(new Date(92,5,10));
        searchFilter.setEnd(new Date(93,12,30));
        searchFilter.setType(SearchFilter.Type.BIRTH);
        List<SearchFilter> list = new ArrayList<>();
        list.add(searchFilter);
        SearchFilter b = new SearchFilter();
        b.setStart("1,2,3");
        b.setType(SearchFilter.Type.OCCUPATION);
        list.add(b);
        searchDTO.setKey(keyWord);
        searchDTO.setFilters(list);
        System.out.println(employeeEntityMapper.listBySearchDTO(searchDTO));
    }


    @Test
    public void test9(){
        Long a = 1000*60*60*24*365L;
        a = a*30;
        System.out.println(a);
//        System.out.println("3-".substring(0,"3-".lastIndexOf("-")));
//        Date date1 = new Date(92,5,10);
//        Date date2 = new Date(93,12,30);
//        System.out.println(date2.getTime()-date1.getTime());
//        Double a = Double.valueOf((date2.getTime()-date1.getTime())/(1000*60*60*24*365L));
//        System.out.println(a);

    }

    @Test
    public void findAvgScore(){
        float attitude=0,ability=0;
        AvgScoreVO avgScoreVO = new AvgScoreVO();
        List<CareerEntity> careerEntities = careerEntityMapper.selectAllByEmpid(2);
        System.out.println(careerEntities.size());
        for (CareerEntity careerEntity:careerEntities){
            attitude+=careerEntity.getAttendance();
            ability+=careerEntity.getPerformance();
        }
        avgScoreVO.setAttitude(attitude/careerEntities.size());
        avgScoreVO.setAbility(ability/careerEntities.size());
        System.out.println(avgScoreVO);
    }

    @Test
    public void findAllCrime(){
        List<CrimeVO> list = new ArrayList<>();
        List<CrimeEntity> crimeEntities=crimeEntityMapper.selectAllByEmpid(2);
        for (CrimeEntity crimeEntity:crimeEntities){
            CrimeVO crimeVO = new CrimeVO();
            BeanUtils.copyProperties(crimeEntity,crimeVO);
            crimeVO.setManager(managerEntityMapper.selectByPrimaryKey(crimeEntity.getManid()).getName());
            list.add(crimeVO);
        }
        System.out.println(list);
    }

    @Test
    public void findCurAttendance(){
        List<AttendanceVO> list=new ArrayList<>();
        List<AttendanceEntity> attendanceEntities = mapper.selectAllByCarid(1);
        for (AttendanceEntity attendanceEntity:attendanceEntities){
            AttendanceVO attendanceVO = new AttendanceVO();
            ManagerEntity managerEntity = managerEntityMapper.selectByPrimaryKey(attendanceEntity.getManid());
            BeanUtils.copyProperties(attendanceEntity,attendanceVO);
            attendanceVO.setManager(managerEntity.getName());
            list.add(attendanceVO);

        }
        System.out.println(list);
    }

    @Test
    public void findCurPerformance(){
        List<PerformanceVO> list=new ArrayList<>();
        List<PerformanceEntity> performanceEntities= performanceEntityMapper.selectAllByCarid(1);
        for (PerformanceEntity performanceEntity:performanceEntities){
            PerformanceVO performanceVO=new PerformanceVO();
            ManagerEntity managerEntity = managerEntityMapper.selectByPrimaryKey(performanceEntity.getManid());
            BeanUtils.copyProperties(performanceEntity,performanceVO);
            performanceVO.setManager(managerEntity.getName());
            list.add(performanceVO);
        }
        System.out.println(list);
    }

    @Test
    public void findCurCrime(){
        List<CrimeVO> list=new ArrayList<>();
        List<CrimeEntity> crimeEntities=crimeEntityMapper.selectAllByCarid(2);
        for (CrimeEntity crimeEntity:crimeEntities){
            CrimeVO crimeVO=new CrimeVO();
            ManagerEntity managerEntity = managerEntityMapper.selectByPrimaryKey(crimeEntity.getManid());
            BeanUtils.copyProperties(crimeEntity,crimeVO);
            crimeVO.setManager(managerEntity.getName());
            list.add(crimeVO);
        }
        System.out.println(list);
    }

    @Test
    public void hrSearch(){
        List<HrVO> list=new ArrayList<>();
        List<HrEntity> hrEntities = hrEntityMapper.selectAll();
        for (HrEntity hrEntity:hrEntities){
            HrVO hrVO = new HrVO();
            EnterpriseEntity enterpriseEntity = enterpriseEntityMapper.selectByPrimaryKey(hrEntity.getEntid());
            BeanUtils.copyProperties(hrEntity,hrVO);
            hrVO.setEnterprise(enterpriseEntity.getName());
            list.add(hrVO);
        }
        System.out.println(list);
    }

    @Test
    public void managerSearch() {
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
        System.out.println(list);
    }
    @Test
    public void departmentSearch(){
        List<DepartmentEntity> list = departmentEntityMapper.selectAll();
        System.out.println(list);
    }
  
    @Test
    public void findAllCareer() {
        List<CareerEntity> careerEntities = careerEntityMapper.selectAllByEmpid(2);
        List<CareerVO> list = new ArrayList<>();
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
        System.out.println(list);

}
