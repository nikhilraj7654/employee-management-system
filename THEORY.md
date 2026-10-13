# THEORY.md: Employee Management System (interview ke liye)

Har resume bullet ka matlab, code kahan hai, aur kya poochha ja sakta hai.

## 1. Layered architecture (Controller -> Service -> Repository)
- **Controller** (`EmployeeController`): HTTP request receive karta hai, response bhejta hai. Business logic nahi likhte.
- **Service** (`EmployeeService`): business rules yahan hote hain (jaise duplicate email check).
- **Repository** (`EmployeeRepository`): database se baat karta hai.
- **Fayda:** har layer ka alag kaam, code saaf rehta hai, badalna aur test karna aasan.
- Flow: Browser -> Controller -> Service -> Repository -> DB, aur response wapas usi raaste.

## 2. REST API aur CRUD
- REST me URL resource ko dikhata hai (`/api/employees`), aur HTTP method action batata hai.
- POST = create, GET = read, PUT = update, DELETE = delete.
- Status codes: 200 OK, 201 Created, 204 No Content, 400 Bad Request, 404 Not Found.
- `@RestController` = `@Controller` + `@ResponseBody`, yani return value seedha JSON ban jati hai.
- `@PathVariable` URL ke andar ka `{id}` leta hai, `@RequestParam` query (`?department=IT`) leta hai, `@RequestBody` JSON ko Java object banata hai.

## 3. Spring Data JPA / Hibernate
- **JPA** ek specification hai (object <-> table mapping), **Hibernate** uska implementation.
- `@Entity` class table ban jati hai, `@Id` primary key, `@GeneratedValue(IDENTITY)` id auto-increment.
- `JpaRepository<Employee, Long>` extend karne se `save`, `findAll`, `findById`, `delete` bina SQL likhe mil jate hain.
- `findByDepartmentIgnoreCase(...)` aur `existsByEmail(...)` **derived query methods** hain. Spring method ke naam se SQL bana leta hai.
- `spring.jpa.hibernate.ddl-auto=update` table apne aap bana deta hai.
- **H2** in-memory DB hai (app band = data gaya). **MySQL** permanent DB hai, `mysql` profile se chalta hai.

## 4. Data modeling
- Ek table `employees`: id (PK), name, email (unique), department, designation, salary.
- `email` par `@Column(unique = true)` lagaya hai taaki duplicate na ho.
- Agar aur badhana ho: alag `departments` table banake Employee ke saath many-to-one relation.

## 5. Validation (Bean Validation)
- Entity par `@NotBlank`, `@Email`, `@Positive` lage hain, controller me `@Valid` se check hota hai.
- Galat input par `MethodArgumentNotValidException` aata hai, jise handler 400 ke saath field-wise messages me badalta hai.

## 6. Exception handling
- `ResourceNotFoundException` apni custom exception hai, jab id nahi milti tab service throw karti hai.
- `@RestControllerAdvice` (`GlobalExceptionHandler`) saari controllers ke liye ek jagah error handle karta hai:
  - not found -> 404, duplicate email -> 400, validation error -> 400.
- **Fayda:** har controller me try/catch nahi likhna padta.

## 7. Frontend aur Ajax (`static/index.html`)
- Ajax = page reload kiye bina server se data lena/bhejna.
- Yahan `fetch()` use hua hai (modern tareeka). `async/await` se response ka wait karte hain aur `res.json()` se JSON padhte hain.
- Table `innerHTML` update karke dobara draw hoti hai (DOM manipulation).
- jQuery me isi kaam ke liye `$.ajax()` / `$.get()` hota hai.
- Spring Boot `static/` folder ki files apne aap serve karta hai, isliye alag frontend server nahi chahiye.

## Interview Q&A
1. **Controller me business logic kyun nahi?** Har layer ka ek kaam hona chahiye, warna code messy aur test karna mushkil ho jata hai.
2. **PUT aur POST me fark?** POST naya resource banata hai, PUT existing ko update karta hai.
3. **JPA aur Hibernate?** JPA specification hai, Hibernate uska implementation.
4. **@Autowired / constructor injection kya hai?** Spring khud object bana ke deta hai (dependency injection). Yahan constructor se inject ho raha hai, jo best practice hai.
5. **H2 ki jagah MySQL kaise?** `mysql` profile chalao aur `application-mysql.properties` me credentials daalo. Code nahi badalta.
6. **Duplicate email kaise roka?** DB me unique constraint, aur service me `existsByEmail` se pehle hi check, taaki saaf error message mile.
7. **404 kaise return hota hai?** Service `ResourceNotFoundException` throw karti hai, `@RestControllerAdvice` use 404 me badal deta hai.
8. **Agar 1 lakh employees hon?** Pagination (`Pageable`) use karenge taaki `findAll` sab ek saath na laye.
9. **Security?** Abhi nahi hai. Aage Spring Security + JWT add kar sakte hain.
