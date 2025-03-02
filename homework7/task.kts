import kotlin.random.Random

// Создание массивы names и surnames
val names = arrayOf("John", "Aaron", "Tim", "Ted", "Steven")
val surnames = arrayOf("Smith", "Dow", "Isaacson", "Pennyworth", "Jankins")

// Создание класса Employee
class Employee(
    val name: String,
    val surname: String,
    val salary: Int
)

fun main() {
    // Создаю массив employees и заполняю его сотрудниками
    val employees = Array(10) {
        Employee(
            name = names.random(),                // Случайное имя
            surname = surnames.random(),          // Случайная фамилия
            salary = Random.nextInt(1000, 2001)   // Зарплата от $1000 до $2000
        )
    }

    // Перебираю массив employees и выводжу информацию по каждому сотруднику
    for (employee in employees) {
        println("${employee.name} ${employee.surname}'s salary is $${employee.salary}")
    }

    // Создаю новый массив сотрудников с четной зарплатой и выводим их информацию
    val employeesWithEvenSalary = employees.filter { it.salary % 2 == 0 }
    println("\nEmployees with even salaries:")
    for (employee in employeesWithEvenSalary) {
        println("${employee.name} ${employee.surname}'s salary is $${employee.salary}")
    }
}
