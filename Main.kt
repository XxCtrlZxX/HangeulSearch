import HangeulUtil.Companion.convertHangeulFinalSound
import HangeulUtil.Companion.getFinalSound
import HangeulUtil.Companion.getInitSound

fun main() {
    // 검색 될 문자열
    val arr = arrayOf("지옥", "월식", "건국대", "페이커", "미세먼지", "ABC마트", "브다샤펄", "대성", "인천 여경", "구구대")
    // 띄어쓰기 없애기
    val arr2 = arr.map { it.replace(" ", "") }

    // 비교할 문자열
    val compareString = "징"

    val findArr = arr2.filter {
        it.upgradedContains(compareString)
    }

    println(findArr)
}


fun String.upgradedContains(s: String):
        Boolean = this.contains(s) || this.containsHangeul(s) || this.asdf(s)


fun String.containsHangeul(s: String): Boolean {

    // 입력된 문자열 중 초성이 하나라도 있으면
    if (s.any { it in 'ㄱ'..'ㅎ' }) {
        val targetString = this
        val range = targetString.length - s.length

        // 맞는 조합이 하나라도 있으면 true
        return (0..range).any { i ->
            var t = i
            return@any s.all {
                when (it) {
                    in 'ㄱ'..'ㅎ' -> { // 초성이면 초성끼리만 비교
                        val targetInit = getInitSound(targetString[t++])
                        return@all it == targetInit
                    }
                    else -> return@all it == targetString[t++]
                }
            }
        }
    }
    return false
}

// TODO: 이름을 뭐라고 짓지
fun String.asdf(s: String): Boolean {
    // 마지막 글자에 받침이 있으면
    getFinalSound(s.last())?.let {
        val convertedString = convertHangeulFinalSound(s)
        return this.containsHangeul(convertedString)
    }
    return false
}