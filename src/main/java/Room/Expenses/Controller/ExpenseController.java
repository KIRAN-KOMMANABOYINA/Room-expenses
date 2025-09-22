package Room.Expenses.Controller;

import Room.Expenses.Entity.Expense;
import Room.Expenses.Repository.ExpenseRepository;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ExpenseController {

    private final ExpenseRepository repo;

    public ExpenseController(ExpenseRepository repo) {
        this.repo = repo;
    }

    // Default Home Page → Add Expense Tab
    @GetMapping("/")
    public String index(Model model,
                        @RequestParam(required = false) String activeTab) {
        model.addAttribute("expenses", repo.findAll());
        model.addAttribute("expense", new Expense());
        model.addAttribute("activeTab", activeTab != null ? activeTab : "add");
        return "index";
    }

    // Save Expense
    @PostMapping("/add")
    public String addExpense(@ModelAttribute Expense expense) {
        repo.save(expense);
        // Redirect back to show all expenses
        return "redirect:/?activeTab=all";
    }

    // Search by Month & Year
    @PostMapping("/search")
    public String searchExpenses(@RequestParam int month,
                                 @RequestParam int year,
                                 Model model) {
        List<Expense> expenses = repo.findByMonthAndYear(month, year);

        double total = expenses.stream().mapToDouble(Expense::getPrice).sum();

        model.addAttribute("expenses", repo.findAll()); // keep all for All Transactions tab
        model.addAttribute("expense", new Expense());
        model.addAttribute("searchResults", expenses);
        model.addAttribute("total", total);
        model.addAttribute("month", month);
        model.addAttribute("year", year);
        model.addAttribute("activeTab", "search");
        return "index";
    }
}
