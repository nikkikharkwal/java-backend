import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootApplication
@RestController
public class TodoListApplication {

  private Map<Integer, TodoTask> todos = new HashMap<>();
  private int currentId = 0;

  public static void main(String[] args) {
    SpringApplication.run(TodoListApplication.class, args);
  }

  @GetMapping("/todos")
  public List<TodoTask> getTodos() {
    return new ArrayList<>(todos.values());
  }

  @PostMapping("/todos")
  public TodoTask addTodo(@RequestBody TodoTask todoTask) {
    todoTask.setId(++currentId);
    todos.put(todoTask.getId(), todoTask);
    return todoTask;
  }

  @PutMapping("/todos/{id}")
  public TodoTask updateTodo(@PathVariable int id, @RequestBody TodoTask todoTask) {
    if (todos.containsKey(id)) {
      todoTask.setId(id);
      todos.put(id, todoTask);
    }
    return todoTask;
  }

  @DeleteMapping("/todos/{id}")
  public void deleteTodo(@PathVariable int id) {
    todos.remove(id);
  }

  static class TodoTask {
    private int id;
    private String task;

    public int getId() {
      return id;
    }

    public void setId(int id) {
      this.id = id;
    }

    public String getTask() {
      return task;
    }

    public void setTask(String task) {
      this.task = task;
    }
  }
}
