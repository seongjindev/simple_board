package sj.simpleboard.repository;

import org.springframework.stereotype.Repository;
import sj.simpleboard.domain.Board;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Repository
public class MemoryBoardRepository implements BoardRepository { //command + shift + T 테스트 코드

    private static final Map<Long, Board> store = new HashMap<>(); //동시성을 고려한다면 concurrentHashMap
    private static long sequence = 0L; //동시성을 고려한다면 AtomicLong

    @Override
    public Board save(Board board) {
        board.setNo(++sequence);
        store.put(board.getNo(), board);
        return board;
    }

    @Override
    public Board findByNo(Long no) { //null이 발생할 수 있으니 null을 처리할 때 optional을 사용한다
        return store.get(no);
    }

    @Override
    public void update(Long no, Board updateParam) {
        Board findNo = store.get(no);
        String now = LocalDateTime.now().toString();
        findNo.setTitle(updateParam.getTitle());
        findNo.setContents(updateParam.getContents());
        findNo.setDate(now);
    }

    @Override
    public void clearStore() {
        store.clear();
    }
}