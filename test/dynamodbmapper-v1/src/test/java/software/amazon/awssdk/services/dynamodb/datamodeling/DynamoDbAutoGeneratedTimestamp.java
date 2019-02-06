/*
 * Copyright 2010-2019 Amazon.com, Inc. or its affiliates. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License").
 * You may not use this file except in compliance with the License.
 * A copy of the License is located at
 *
 *  http://aws.amazon.com/apache2.0
 *
 * or in the "license" file accompanying this file. This file is distributed
 * on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either
 * express or implied. See the License for the specific language governing
 * permissions and limitations under the License.
 */

package software.amazon.awssdk.services.dynamodb.datamodeling;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Date;

/**
 * Annotation for auto-generating a date/timestamp.
 *
 * <pre class="brush: java">
 * &#064;DynamoDBAutoGeneratedTimestamp(strategy=DynamoDBAutoGenerateStrategy.CREATE)
 * public Date getCreatedDate() { return createdDate; }
 * public void setCreatedDate(Date createdDate) { this.createdDate = createdDate; }
 *
 * &#064;DynamoDBAutoGeneratedTimestamp(strategy=DynamoDBAutoGenerateStrategy.ALWAYS)
 * public Date lastUpdatedDate() { return lastUpdatedDate; }
 * public void setLastUpdatedDate(Date lastUpdatedDate) { this.lastUpdatedDate = lastUpdatedDate; }
 * </pre>
 *
 * <p>Supports the standard {@link Date} type-conversions; such as
 * {@link java.util.Calendar}, {@link Long}.</p>
 *
 * <p>Primitives such as {@code long} are not supported since the unset
 * (or null) state can't be detected.</p>
 *
 * <p>Compatible with {@link DynamoDbTypeConvertedTimestamp}</p>
 */
@DynamoDbAutoGenerated(generator = DynamoDbAutoGeneratedTimestamp.Generator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.METHOD})
public @interface DynamoDbAutoGeneratedTimestamp {

    /**
     * The auto-generation strategy; default is {@code ALWAYS}.
     * @see DynamoDbAutoGenerateStrategy
     */
    DynamoDbAutoGenerateStrategy strategy() default DynamoDbAutoGenerateStrategy.ALWAYS;

    /**
     * Default generator.
     */
    final class Generator<T> extends DynamoDbAutoGenerator.AbstractGenerator<T> {
        private final DynamoDbTypeConverter<T, Date> converter;

        Generator(Class<T> targetType, DynamoDbAutoGeneratedTimestamp annotation) {
            super(annotation.strategy());
            this.converter = StandardTypeConverters.factory().getConverter(targetType, Date.class);
        }

        @Override
        public T generate(T currentValue) {
            return converter.convert(new Date());
        }
    }

}
