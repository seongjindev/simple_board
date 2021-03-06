package sj.simpleboard.repository;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import sj.simpleboard.domain.Board;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@Slf4j
//@Repository
public class MemoryBoardRepository implements BoardRepository { //command + shift + T 테스트 코드

    private static final Map<Long, Board> store = new HashMap<>(); //동시성을 고려한다면 concurrentHashMap
    private static long sequence = 0L; //동시성을 고려한다면 AtomicLong

    @Override
    public void boardSave(Board board) {
        board.setSeq(++sequence);
        store.put(board.getSeq(), board);
    }

    @Override
    public Board boardFindByNo(Long no) { //null이 발생할 수 있으니 null을 처리할 때 optional을 사용한다
        return store.get(no);
    }

    @Override
    public void boardUpdate(Long no, Board updateParam) {
        Board findNo = store.get(no);
        findNo.setTitle(updateParam.getTitle());
        findNo.setContents(updateParam.getContents());
    }
    @Override
    public List<Board> boardFindAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public void boardDelete(Long no) {
        store.remove(no);
    }

    @Override
    public void clearStore() {
        store.clear();
    }

    @Override
    public Board boardFindNum() {
        Board byNo = boardFindByNo(sequence);
        return byNo;
    }
}
