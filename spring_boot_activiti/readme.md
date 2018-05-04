## Setup IDEA Plugin
Jboss jBPMN(default recommended)
### 参考链接
https://www.activiti.org/userguide/index.html
## 7. BPMN 2.0 Introduction
### 7.2. Defining a process
创建一个文件后缀名为.bpmn20.xml or .bpmn结尾
````
<definitions
  xmlns="http://www.omg.org/spec/BPMN/20100524/MODEL"
  xmlns:activiti="http://activiti.org/bpmn"
  targetNamespace="Examples">

  <process id="myProcess" name="My First Process">
    ..
  </process>

</definitions>
````

````
ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("myProcess");
````
### 7.3.5. XML representation
* start event 告知其流程的入口
* User Tasks 代表其流程中的人工处理任务
* end event 告知其流程结束
* sequence flows 对其流程的各个元素进行链接
````
<definitions id="definitions"
  targetNamespace="http://activiti.org/bpmn20"
  xmlns:activiti="http://activiti.org/bpmn"
  xmlns="http://www.omg.org/spec/BPMN/20100524/MODEL">

	<process id="financialReport" name="Monthly financial report reminder process">

	  <startEvent id="theStart" />

	  <sequenceFlow id="flow1" sourceRef="theStart" targetRef="writeReportTask" />

	  <userTask id="writeReportTask" name="Write monthly financial report" >
	    <documentation>
	      Write monthly financial report for publication to shareholders.
	    </documentation>
	    <potentialOwner>
	      <resourceAssignmentExpression>
	        <formalExpression>accountancy</formalExpression>
	      </resourceAssignmentExpression>
	    </potentialOwner>
	  </userTask>

	  <sequenceFlow id="flow2" sourceRef="writeReportTask" targetRef="verifyReportTask" />

	  <userTask id="verifyReportTask" name="Verify monthly financial report" >
	    <documentation>
	      Verify monthly financial report composed by the accountancy department.
	      This financial report is going to be sent to all the company shareholders.
	    </documentation>
	    <potentialOwner>
	      <resourceAssignmentExpression>
	        <formalExpression>management</formalExpression>
	      </resourceAssignmentExpression>
	    </potentialOwner>
	  </userTask>

	  <sequenceFlow id="flow3" sourceRef="verifyReportTask" targetRef="theEnd" />

	  <endEvent id="theEnd" />

	</process>

</definitions>
````
### 7.3.6. Starting a process instance
* idea file path : resources/processes/FinancialReportProcess.bpmn20.xml
````
Deployment deployment = repositoryService.createDeployment()
  .addClasspathResource("FinancialReportProcess.bpmn20.xml")
  .deploy();
````
````
ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("financialReport");
````
 每一个新的元素被执行，Activiti engine 会存储一个新的Task至持久化数据库中。
 当Activiti engine重新启动或挂掉，进程的状态也很好的保存在数据库中。
 After the task is created, the startProcessInstanceByKey method will return since the user task activity is a wait state. In this case, the task is assigned to a group, which means that every member of the group is a candidate to perform the task.
 
 ````
 public static void main(String[] args) {
 
   // Create Activiti process engine
   ProcessEngine processEngine = ProcessEngineConfiguration
     .createStandaloneProcessEngineConfiguration()
     .buildProcessEngine();
 
   // Get Activiti services
   RepositoryService repositoryService = processEngine.getRepositoryService();
   RuntimeService runtimeService = processEngine.getRuntimeService();
 
   // Deploy the process definition
   repositoryService.createDeployment()
     .addClasspathResource("FinancialReportProcess.bpmn20.xml")
     .deploy();
 
   // Start a process instance
   runtimeService.startProcessInstanceByKey("financialReport");
 }
````
### 7.3.7. Task lists

````
TaskService taskService = processEngine.getTaskService();
List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup("accountancy").list();
````

### 7.3.9. Completing the task

````
taskService.complete(task.getId());
````
### 7.3.10. Ending the process

Programmatically, you can also verify that the process is ended using the historyService

````
HistoryService historyService = processEngine.getHistoryService();
HistoricProcessInstance historicProcessInstance =
historyService.createHistoricProcessInstanceQuery().processInstanceId(procId).singleResult();
System.out.println("Process instance end time: " + historicProcessInstance.getEndTime());
````
### 7.3.11. Code overview
````
public class TenMinuteTutorial {

  public static void main(String[] args) {

    // Create Activiti process engine
    ProcessEngine processEngine = ProcessEngineConfiguration
      .createStandaloneProcessEngineConfiguration()
      .buildProcessEngine();

    // Get Activiti services
    RepositoryService repositoryService = processEngine.getRepositoryService();
    RuntimeService runtimeService = processEngine.getRuntimeService();

    // Deploy the process definition
    repositoryService.createDeployment()
      .addClasspathResource("FinancialReportProcess.bpmn20.xml")
      .deploy();

    // Start a process instance
    String procId = runtimeService.startProcessInstanceByKey("financialReport").getId();

    // Get the first task
    TaskService taskService = processEngine.getTaskService();
    List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup("accountancy").list();
    for (Task task : tasks) {
      System.out.println("Following task is available for accountancy group: " + task.getName());

      // claim it
      taskService.claim(task.getId(), "fozzie");
    }

    // Verify Fozzie can now retrieve the task
    tasks = taskService.createTaskQuery().taskAssignee("fozzie").list();
    for (Task task : tasks) {
      System.out.println("Task for fozzie: " + task.getName());

      // Complete the task
      taskService.complete(task.getId());
    }

    System.out.println("Number of tasks for fozzie: "
            + taskService.createTaskQuery().taskAssignee("fozzie").count());

    // Retrieve and claim the second task
    tasks = taskService.createTaskQuery().taskCandidateGroup("management").list();
    for (Task task : tasks) {
      System.out.println("Following task is available for management group: " + task.getName());
      taskService.claim(task.getId(), "kermit");
    }

    // Completing the second task ends the process
    for (Task task : tasks) {
      taskService.complete(task.getId());
    }

    // verify that the process is actually finished
    HistoryService historyService = processEngine.getHistoryService();
    HistoricProcessInstance historicProcessInstance =
      historyService.createHistoricProcessInstanceQuery().processInstanceId(procId).singleResult();
    System.out.println("Process instance end time: " + historicProcessInstance.getEndTime());
  }

}
````
## 8. BPMN 2.0 Constructs
#### 8.2.2. Timer Event Definitions