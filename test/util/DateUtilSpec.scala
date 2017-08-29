package util

import org.specs2.mutable.Specification
import org.joda.time.LocalDate

class DateUtilSpec extends Specification {

  "DateUtil" should {
    "compare" in{
      DateUtil.noEndDate(LocalDate.now()) must equalTo("")
    }
  }

}
