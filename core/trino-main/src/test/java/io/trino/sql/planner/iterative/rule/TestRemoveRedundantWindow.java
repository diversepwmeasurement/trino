/*
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.trino.sql.planner.iterative.rule;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import io.trino.sql.planner.iterative.rule.test.BaseRuleTest;
import io.trino.sql.planner.plan.DataOrganizationSpecification;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static io.trino.sql.planner.assertions.PlanMatchPattern.values;

public class TestRemoveRedundantWindow
        extends BaseRuleTest
{
    @Test
    public void testPruneWhenSourceEmpty()
    {
        tester().assertThat(new RemoveRedundantWindow())
                .on(p -> p.window(
                        new DataOrganizationSpecification(ImmutableList.of(), Optional.empty()),
                        ImmutableMap.of(),
                        p.values(p.symbol("a"))))
                .matches(
                        values("a"));
    }

    @Test
    public void testDoNotPruneWhenSourceNotEmpty()
    {
        tester().assertThat(new RemoveRedundantWindow())
                .on(p -> p.window(
                        new DataOrganizationSpecification(ImmutableList.of(), Optional.empty()),
                        ImmutableMap.of(),
                        p.values(5, p.symbol("a"))))
                .doesNotFire();
    }
}
