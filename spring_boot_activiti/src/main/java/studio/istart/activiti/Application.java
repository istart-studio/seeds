package studio.istart.activiti;

import org.activiti.engine.IdentityService;
import org.activiti.engine.identity.Group;
import org.activiti.engine.identity.User;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

/**
 * @author DongYan
 * @version 1.0.0
 * @since 1.8
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

//    @Bean
//    CommandLineRunner init(final RepositoryService repositoryService,
//                           final RuntimeService runtimeService,
//                           final TaskService taskService) {
//
//        return new CommandLineRunner() {
//            @Override
//            public void run(String... strings) throws Exception {
//                Map<String, Object> variables = new HashMap<String, Object>();
//                variables.put("applicantName", "Dong yan");
//                variables.put("email", "7632632@qq.com");
//                variables.put("phoneNumber", "000000000");
//                runtimeService.startProcessInstanceByKey("hireProcess", variables);
//            }
//        };
//    }

    @Bean
    InitializingBean usersAndGroupsInitializer(final IdentityService identityService) {

        return new InitializingBean() {
            @Override
            public void afterPropertiesSet() throws Exception {

                Group group = identityService.newGroup("user");
                group.setName("users");
                group.setType("security-role");
                identityService.saveGroup(group);

                User admin = identityService.newUser("admin");
                admin.setPassword("admin");
                identityService.saveUser(admin);

            }
        };
    }
}
