package io.lhysin.common.provider

/**
 * 페이지된 데이터를 chunkSize 만큼 전체 로드한다.
 */
class PagedProvider<T>(
    private val chunkSize: Int
) {

    /**
     * chunkSize 만큼 조회 후 조회된 전체 데이터를 반한한다.
     * @param read 조회
     */
    fun paged(read: (Int, Int) -> List<T>): List<T> {
        var offset = 0
        val results = mutableListOf<T>()
        do {
            val list = read(offset, chunkSize)

            results.addAll(list)

            offset += chunkSize

            // 만약 로드된 데이터의 사이즈가 chunkSize보다 작거나 0인 경우 루프 종료.
            if (list.size < chunkSize || list.isEmpty()) {
                break
            }
        } while (true) // 무조건 한 번은 실행하고 나중에 조건을 검사하여 루프 종료.
        return results
    }

    /**
     * chunkSize 만큼 조회 후 데이터를 소비 시킨다.
     * @param read 조회
     */
    fun paged(read: (Int, Int) -> List<T>, consume: (List<T>) -> Unit) {
        var offset = 0
        do {
            val list = read(offset, chunkSize)

            consume(list)

            offset += chunkSize

            // 만약 로드된 데이터의 사이즈가 chunkSize보다 작거나 0인 경우 루프 종료.
            if (list.size < chunkSize || list.isEmpty()) {
                break
            }
        } while (true) // 무조건 한 번은 실행하고 나중에 조건을 검사하여 루프 종료.
    }
}