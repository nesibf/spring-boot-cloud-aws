/*
 * Copyright 2013-2022 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.awspring.cloud.test.sqs;

import io.awspring.cloud.autoconfigure.context.ContextCredentialsAutoConfiguration;
import io.awspring.cloud.autoconfigure.context.ContextRegionProviderAutoConfiguration;
import io.awspring.cloud.autoconfigure.messaging.SqsAutoConfiguration;
import io.awspring.cloud.messaging.core.QueueMessagingTemplate;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jackson.JacksonAutoConfiguration;
import org.springframework.context.ApplicationContext;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@SqsTest
class SqsTestNoListenersDefinedTest extends BaseSqsIntegrationTest {

	private static final Class<?>[] EXPECTED_AUTOCONFIGURATION_CLASSES = { SqsAutoConfiguration.class,
			JacksonAutoConfiguration.class, ContextRegionProviderAutoConfiguration.class,
			ContextCredentialsAutoConfiguration.class };

	@Autowired
	private ApplicationContext ctx;

	@Test
	void usesAutoConfigurations() {
		for (Class<?> clazz : EXPECTED_AUTOCONFIGURATION_CLASSES) {
			assertThatNoException().isThrownBy(() -> this.ctx.getBean(clazz));
		}
	}

	@Test
	void createsQueueMessagingTemplate() {
		assertThatNoException().isThrownBy(() -> this.ctx.getBean(QueueMessagingTemplate.class));
	}

	@Test
	void doesNotCreateListenerBean() {
		assertThatThrownBy(() -> this.ctx.getBean(SqsSampleListener.class))
				.isInstanceOf(NoSuchBeanDefinitionException.class);
	}

	@Test
	void doesNotCreateOtherBeans() {
		assertThatThrownBy(() -> this.ctx.getBean(SampleComponent.class))
				.isInstanceOf(NoSuchBeanDefinitionException.class);
	}

}
