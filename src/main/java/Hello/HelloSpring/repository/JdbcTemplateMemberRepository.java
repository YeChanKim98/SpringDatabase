package Hello.HelloSpring.repository;

import Hello.HelloSpring.domain.member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;

import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class JdbcTemplateMemberRepository implements MemberRepository{

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    // 생성자가 1개이면, 빈으로 등록시 @Autowired태그를 생략할 수 있다.
    public JdbcTemplateMemberRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public member save(member member) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("member").usingGeneratedKeyColumns("id"); // member테이블의 현재 키를 보고 다음 키 자동 생성
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", member.getName()); // 인자로 받은 가입할 Name을 해쉬맵에 저장
        Number key = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters)); // 자동 생성된 키와 맵으로 저장된 Name을 인서트 익스큐트 후 결과값으로 생성된 키값을 리턴받는다
        member.setId(key.longValue()); // 생성받은 키값을 꺼내서 member에 입력한다
        return member;
    }

    @Override
    public Optional<member> findById(Long id) {
        List<member> result = jdbcTemplate.query("select * from member where id = ?", memberRowMapper(),id); // jdbcTemplate.query()는 반환값이 List<T>. 따라서 쿼리 후에 해당 T로 자료셋을 맞추기 위한 RowMapper()를 인자로 주고, 마지막으로 ?에 들어갈 id를 인자로 넘긴다
        return result.stream().findAny(); // 컬렉션의 저장 요소를 하나씩 참조해서 람다식으로 처리할 수 있도록 해주는 반복자
    }

    @Override
    // Optional<member> : 반환형은 member이지만 null값에 대한 반환처리를 하기위해 Optional사용
    public Optional<member> findByName(String name) {
        List<member> result = jdbcTemplate.query("select * from member where name = ?", memberRowMapper(),name);
        return result.stream().findAny();
    }

    @Override
    public List<member> findAll() {
        return jdbcTemplate.query("select * from member", memberRowMapper());
    }

    // 포멧팅을 위한 로우맵퍼
    private RowMapper<member> memberRowMapper(){
        // jdbctamplate의 쿼리 실행결과를 vo객체에 넣어서 반환 -> 일반적인 jdbc코딩시 결과로 받는 rs와 동일
        return new RowMapper<member>() {
            @Override
            public member mapRow(ResultSet rs, int rowNum) throws SQLException {
                member member = new member();
                member.setId(rs.getLong("id"));
                member.setName(rs.getString("name"));
                return member;
            }
        };
        // Alt + Enter키를 통해서 람다형식으로 변경가능
    }
}
