import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import javax.persistence.*;
import java.util.List;

@SpringBootApplication
@RestController
public class Application {

  @Entity
  public static class Todo {
    @Id
    @GeneratedValue
    private Long id;

    private String task;

    public Todo() {}

    public Todo(String task) {
      this.task = task;
    }

    public Long getId() {
      return id;
    }

    public String getTask() {
      return task;
    }

    public void setTask(String task) {
      this.task = task;
    }
  }

  @Repository
  interface TodoRepository extends JpaRepository<Todo, Long> {}

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @GetMapping("/todos")
  public List<Todo> todos(TodoRepository todoRepository) {
    return todoRepository.findAll();
  }

  @PostMapping("/todos")
  public Todo addTodo(@RequestBody Todo todo, TodoRepository todoRepository) {
    return todoRepository.save(todo);
  }

  @PutMapping("/todos/{id}")
  public Todo updateTodo(@PathVariable Long id, @RequestBody Todo todo, TodoRepository todoRepository) {
    Todo existingTodo = todoRepository.findById(id).orElse(null);
    if (existingTodo != null) {
      existingTodo.setTask(todo.getTask());
      return todoRepository.save(existingTodo);
    }
    return null;
  }

  @DeleteMapping("/todos/{id}")
  public void deleteTodo(@PathVariable Long id, TodoRepository todoRepository) {
    todoRepository.deleteById(id);
  }
}
