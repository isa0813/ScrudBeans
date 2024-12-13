package myjavapackage;

import com.github.manosbatsis.scrudbeans.repository.ModelRepository;
import com.github.manosbatsis.scrudbeans.repository.ModelRepositoryFactoryBean;
import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.format.Formatter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.math.BigDecimal;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Slf4j
// Remove security and error handling
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class, ErrorMvcAutoConfiguration.class})
// Enable transactions and auditing
@EnableTransactionManagement
@EnableJpaAuditing
// Scan for existing or runtime-generated (scrudbeans) components
@EntityScan({ScrudBeansSampleApplication.PACKAGE_NAME})
@EnableJpaRepositories(
        basePackages = {ScrudBeansSampleApplication.PACKAGE_NAME},
        includeFilters = {
                @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {ModelRepository.class})
        },
        repositoryFactoryBeanClass = ModelRepositoryFactoryBean.class
)
public class ScrudBeansSampleApplication {

	public static final String PACKAGE_NAME = "myjavapackage";

	public static void main(String[] args) {
		SpringApplication.run(ScrudBeansSampleApplication.class, args);
	}

	/**
	 * Register a formatter for parsing LocalDateTime
	 * @return
	 */
	@Bean
	public Formatter<LocalDateTime> localDateFormatter() {
		return new Formatter<LocalDateTime>() {
			@Override
			public LocalDateTime parse(String text, Locale locale) throws ParseException {
				return LocalDateTime.parse(text, DateTimeFormatter.ISO_DATE_TIME);
			}

			@Override
			public String print(LocalDateTime object, Locale locale) {
				return DateTimeFormatter.ISO_DATE_TIME.format(object);
			}
		};
	}

	/**
	 * Create sample products for demo purposes
	 */
	
}

