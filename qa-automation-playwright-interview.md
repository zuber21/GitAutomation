# QA Automation with Playwright TypeScript/JavaScript Interview Questions & Answers

## Table of Contents
1. [Foundational Concepts](#foundational-concepts)
2. [Playwright Basics](#playwright-basics)
3. [TypeScript/JavaScript Specific](#typescriptjavascript-specific)
4. [Advanced Testing Scenarios](#advanced-testing-scenarios)
5. [Best Practices & Performance](#best-practices--performance)
6. [Troubleshooting & Debugging](#troubleshooting--debugging)

---

## Foundational Concepts

### Q1: What is QA Automation and why is it important?
**Answer:**
QA Automation is the process of using software tools to automatically execute test cases on an application to verify functionality, performance, and reliability. It's important because:

- **Time Efficiency**: Reduces manual testing time by running repetitive tests automatically
- **Cost-Effective**: Reduces long-term costs despite initial setup investment
- **Consistency**: Tests run the same way every time, eliminating human error
- **Regression Testing**: Quickly identifies bugs introduced by new code changes
- **Continuous Integration/Deployment (CI/CD)**: Enables automated testing in deployment pipelines
- **Coverage**: Can test more scenarios than manual testing in the same timeframe

### Q2: What are the different types of testing?
**Answer:**

| Type | Definition | Example |
|------|-----------|---------|
| **Unit Testing** | Tests individual components or functions | Testing a validation function |
| **Integration Testing** | Tests interactions between multiple components | Testing API calls with database |
| **Functional Testing** | Tests features against requirements | Login functionality |
| **End-to-End (E2E)** | Tests complete user workflows | Complete purchase flow |
| **Performance Testing** | Tests response time and load capacity | Load testing under 1000 users |
| **Security Testing** | Tests for vulnerabilities | SQL injection testing |
| **Regression Testing** | Tests existing functionality after changes | Re-running tests after bug fix |

### Q3: What is Test Automation Framework?
**Answer:**
A Test Automation Framework is a structured set of guidelines and tools for writing and executing test cases. It includes:

- **Architecture**: Organized structure for code organization
- **Libraries**: Reusable components and utilities
- **Best Practices**: Coding standards and conventions
- **Documentation**: Guidelines for writing tests

**Common Frameworks:**
- **Page Object Model (POM)**: Separates test logic from page interactions
- **Data-Driven Framework**: Separates test data from test logic
- **Keyword-Driven Framework**: Uses keywords to describe actions
- **Hybrid Framework**: Combines multiple approaches

---

## Playwright Basics

### Q4: What is Playwright and what are its advantages?
**Answer:**
Playwright is a modern, open-source browser automation framework developed by Microsoft that allows automated testing of web applications across multiple browsers.

**Advantages:**
- **Cross-browser Support**: Chrome, Firefox, Safari, Edge out of the box
- **Multiple Languages**: JavaScript, TypeScript, Python, Java, C#
- **Fast**: Optimized for fast test execution
- **Reliable**: Built-in waiting mechanisms to reduce flakiness
- **Mobile Testing**: Can emulate mobile devices
- **Network Interception**: Mock API responses
- **Screenshots & Videos**: Built-in recording capabilities
- **Headless Mode**: Run without GUI for CI/CD pipelines
- **Accessibility Testing**: Built-in features for accessibility checks

### Q5: What are the different selectors in Playwright?
**Answer:**
Playwright supports multiple selector types:

```typescript
// CSS Selector
page.locator('button.submit');

// XPath
page.locator('//button[text()="Submit"]');

// Text Selector
page.locator('text=Submit');

// Accessible Name Selector
page.locator('[aria-label="Submit"]');

// Role Selector
page.locator('role=button[name="Submit"]');

// Nth-child selector
page.locator('button:nth-of-type(2)');

// Combining selectors
page.locator('form input[type="email"]');
```

**Best Practice**: Use role-based or accessible selectors when possible for better resilience.

### Q6: What is a locator in Playwright?
**Answer:**
A locator is a way to find one or more elements on a page. Unlike selectors which are just strings, locators in Playwright are smart objects that:

- **Auto-wait**: Automatically waits for elements to be visible/stable
- **Retry Logic**: Retries locating elements until timeout
- **Chainable**: Can chain multiple locators together
- **Reusable**: Can be stored and reused multiple times

```typescript
// Creating a locator
const submitButton = page.locator('button:has-text("Submit")');

// Using the locator multiple times
await submitButton.click();
await submitButton.hover();

// Chaining locators
const form = page.locator('form');
const emailInput = form.locator('input[type="email"]');
```

### Q7: What is the difference between `goto()` and `navigate()`?
**Answer:**
In Playwright, there is no `navigate()` method. The correct method is `goto()`:

```typescript
// Correct way
await page.goto('https://example.com');

// With options
await page.goto('https://example.com', {
  waitUntil: 'networkidle', // Wait until network is idle
  timeout: 30000 // Timeout in milliseconds
});
```

**waitUntil options:**
- `'load'`: Wait for page load event
- `'domcontentloaded'`: Wait for DOM to load
- `'networkidle'`: Wait for network requests to complete

### Q8: How do you handle waits in Playwright?
**Answer:**

```typescript
// 1. Auto-wait (Implicit) - Default behavior
// Playwright waits up to 30 seconds for element to be actionable
await page.click('button');

// 2. Explicit waits
// Wait for element to be visible
await page.waitForSelector('button', { visible: true });

// Wait for URL
await page.waitForURL('**/dashboard');

// Wait for function
await page.waitForFunction(() => document.querySelectorAll('li').length > 3);

// Wait for navigation
await page.waitForNavigation();

// Wait for load state
await page.waitForLoadState('networkidle');

// Custom wait with polling
await page.locator('button').waitFor({ state: 'visible', timeout: 5000 });

// 3. Avoid hard waits (antipattern)
// await new Promise(resolve => setTimeout(resolve, 1000)); // DON'T DO THIS
```

**Best Practice**: Use Playwright's built-in waiting mechanisms instead of `setTimeout`.

### Q9: What is Page Object Model (POM) and why use it?
**Answer:**
Page Object Model is a design pattern that creates an abstraction of web pages to improve test maintenance and reduce code duplication.

**Example:**
```typescript
// loginPage.ts
export class LoginPage {
  constructor(private page: Page) {}

  async goto() {
    await this.page.goto('https://example.com/login');
  }

  async login(email: string, password: string) {
    await this.page.fill('input[name="email"]', email);
    await this.page.fill('input[name="password"]', password);
    await this.page.click('button[type="submit"]');
  }

  async getErrorMessage(): Promise<string> {
    return this.page.locator('.error').textContent();
  }
}

// test.spec.ts
test('Login with invalid credentials', async ({ page }) => {
  const loginPage = new LoginPage(page);
  await loginPage.goto();
  await loginPage.login('invalid@test.com', 'wrongpassword');
  
  const error = await loginPage.getErrorMessage();
  expect(error).toContain('Invalid credentials');
});
```

**Advantages:**
- **Maintainability**: Changes to page structure only require updating the page object
- **Reusability**: Methods can be reused across multiple tests
- **Readability**: Tests are more readable and self-documenting
- **Scalability**: Easier to manage large test suites

### Q10: How do you handle multiple tabs/windows in Playwright?
**Answer:**

```typescript
// Listen for new page/tab
const [newPage] = await Promise.all([
  context.waitForEvent('page'),
  page.click('a[target="_blank"]') // Click link that opens new tab
]);

// Use the new page
await newPage.waitForLoadState();
console.log(newPage.url());

// Switch between pages
const pages = context.pages();
const secondPage = pages[1];
await secondPage.bringToFront();

// Close the new page
await newPage.close();
```

---

## TypeScript/JavaScript Specific

### Q11: What is TypeScript and what are its advantages over JavaScript?
**Answer:**
TypeScript is a superset of JavaScript that adds static typing and advanced features.

**Advantages:**
- **Type Safety**: Catches errors at compile time rather than runtime
- **IntelliSense**: Better IDE support and autocomplete
- **Self-Documenting**: Type annotations serve as documentation
- **Large Codebases**: Easier to maintain and scale
- **Refactoring**: Safer refactoring with type checking

**Example:**
```typescript
// TypeScript - Type safe
function login(email: string, password: string): boolean {
  return email.length > 0 && password.length > 0;
}
login('test@example.com', 'password123'); // ✓ Correct
// login('test@example.com', 123); // ✗ Error: Type 'number' is not assignable to type 'string'

// JavaScript - No type checking
function login(email, password) {
  return email.length > 0 && password.length > 0;
}
login('test@example.com', 123); // No error, but causes runtime error
```

### Q12: What is async/await and how is it used in testing?
**Answer:**
Async/await is a modern JavaScript feature for handling asynchronous operations, making async code look and behave more like synchronous code.

```typescript
// Without async/await (Promise-based)
function loginUser() {
  return fillEmail('test@example.com')
    .then(() => fillPassword('password'))
    .then(() => clickLoginButton())
    .then(() => waitForDashboard());
}

// With async/await (cleaner)
async function loginUser() {
  await fillEmail('test@example.com');
  await fillPassword('password');
  await clickLoginButton();
  await waitForDashboard();
}

// Usage in test
test('User login', async () => {
  await loginUser();
  expect(page.url()).toBe('https://example.com/dashboard');
});
```

**Key Points:**
- `async` functions always return a Promise
- `await` pauses execution until Promise resolves
- Must use `await` inside `async` functions
- Makes error handling easier with try/catch

### Q13: What is destructuring and how is it used in Playwright?
**Answer:**
Destructuring allows you to extract values from arrays or properties from objects in a concise way.

```typescript
// Object destructuring
const { page, context, browser } = await browserType.launch();

// Array destructuring
const [newPage] = await Promise.all([
  context.waitForEvent('page'),
  page.click('a[target="_blank"]')
]);

// Parameter destructuring (very common in Playwright)
test('Login test', async ({ page, context }) => {
  await page.goto('https://example.com');
});

// Destructuring with default values
const { timeout = 30000, retry = 3 } = options;

// Renaming during destructuring
const { email: userEmail, password: userPassword } = credentials;
```

### Q14: What is a Promise and how are they used in Playwright?
**Answer:**
A Promise represents the eventual completion (or failure) of an asynchronous operation and its resulting value.

```typescript
// Promise states: pending, fulfilled, rejected

// Creating a Promise
const myPromise = new Promise((resolve, reject) => {
  setTimeout(() => resolve('Success!'), 1000);
});

// Consuming a Promise
myPromise
  .then(result => console.log(result))
  .catch(error => console.error(error));

// Promise in Playwright - handling new pages
const pagePromise = context.waitForEvent('page');
page.click('a[target="_blank"]');
const newPage = await pagePromise;

// Promise.all - wait for multiple operations
const results = await Promise.all([
  page.goto('https://example.com'),
  page.waitForLoadState('networkidle')
]);

// Promise.race - resolve with first completed
const result = await Promise.race([
  page.waitForSelector('.success', { timeout: 5000 }),
  page.waitForSelector('.error', { timeout: 5000 })
]);
```

### Q15: What is Arrow Function syntax and when is it used?
**Answer:**
Arrow functions are a concise syntax for writing functions, introduced in ES6.

```typescript
// Traditional function
function add(a: number, b: number): number {
  return a + b;
}

// Arrow function
const add = (a: number, b: number): number => a + b;

// Multiple lines
const login = async (page: Page, email: string, password: string) => {
  await page.fill('input[name="email"]', email);
  await page.fill('input[name="password"]', password);
  await page.click('button[type="submit"]');
};

// Single parameter - parentheses optional
const formatName = (name) => name.toUpperCase();

// Common in test setup
beforeEach(async () => {
  await page.goto('https://example.com');
});

// Common with callbacks
page.on('console', msg => console.log(msg));
```

**Note**: Arrow functions don't have their own `this` context, which is important when working with class methods.

---

## Advanced Testing Scenarios

### Q16: How do you handle network requests and mocking in Playwright?
**Answer:**

```typescript
// 1. Intercept and mock API responses
await page.route('**/api/users', route => {
  route.abort(); // Block the request
  // OR
  route.continue(); // Allow the request
  // OR
  route.fulfill({
    status: 200,
    contentType: 'application/json',
    body: JSON.stringify({ name: 'John' })
  });
});

// 2. Monitor network requests
const requests: string[] = [];
page.on('request', request => {
  requests.push(request.url());
  console.log('Request:', request.method(), request.url());
});

// 3. Wait for specific network request
const responsePromise = page.waitForResponse(
  response => response.url().includes('api/login') && response.status() === 200
);
await page.click('button[type="submit"]');
const response = await responsePromise;
const json = await response.json();

// 4. Mock failed API responses
await page.route('**/api/data', route => {
  route.fulfill({
    status: 500,
    body: 'Server Error'
  });
});

// 5. Continue with request modifications
await page.route('**/api/**', route => {
  const request = route.request();
  route.continue({
    headers: {
      ...request.headers(),
      'Authorization': 'Bearer token123'
    }
  });
});
```

### Q17: How do you test file uploads and downloads?
**Answer:**

```typescript
// File Upload
test('Upload file', async ({ page }) => {
  // Locate input[type="file"]
  await page.locator('input[type="file"]').setInputFiles('path/to/file.pdf');
  
  // Or multiple files
  await page.locator('input[type="file"]').setInputFiles([
    'file1.pdf',
    'file2.pdf'
  ]);
  
  // Submit the form
  await page.click('button[type="submit"]');
});

// File Download
test('Download file', async ({ page }) => {
  // Wait for download
  const downloadPromise = page.waitForEvent('download');
  await page.click('a[download]'); // Click download link
  
  const download = await downloadPromise;
  
  // Save to path
  await download.saveAs('downloads/' + download.suggestedFilename());
  
  // Verify file path
  console.log(download.path());
});
```

### Q18: How do you handle browser contexts for test isolation?
**Answer:**

```typescript
// Browser contexts provide complete isolation
// Each context has its own cookies, localStorage, sessionStorage, etc.

test('Test with isolated context', async ({ browser }) => {
  // Create new context
  const context = await browser.newContext({
    viewport: { width: 1280, height: 720 },
    locale: 'en-US',
    timezone: 'America/New_York'
  });
  
  const page = await context.newPage();
  await page.goto('https://example.com');
  
  // This context is isolated from others
  await page.fill('input[name="email"]', 'user@example.com');
  
  // Cleanup
  await context.close();
});

// Multiple contexts in same test
test('Test cross-context interactions', async ({ browser }) => {
  const context1 = await browser.newContext();
  const context2 = await browser.newContext();
  
  const page1 = await context1.newPage();
  const page2 = await context2.newPage();
  
  // page1 and page2 are completely isolated
});

// Browser context with authentication
test.use({
  contextOptions: {
    httpCredentials: {
      username: 'user',
      password: 'password'
    }
  }
});
```

### Q19: How do you test keyboard and mouse interactions?
**Answer:**

```typescript
// Keyboard interactions
test('Keyboard interactions', async ({ page }) => {
  // Type text
  await page.type('input[name="search"]', 'playwright');
  
  // Press specific keys
  await page.press('input', 'Enter');
  await page.press('body', 'Escape');
  
  // Hold modifier keys
  await page.keyboard.press('Control+A'); // Select all
  await page.keyboard.press('Control+C'); // Copy
  await page.keyboard.press('Control+V'); // Paste
  
  // Key combinations
  await page.keyboard.down('Shift');
  await page.keyboard.press('ArrowRight');
  await page.keyboard.press('ArrowRight');
  await page.keyboard.up('Shift');
});

// Mouse interactions
test('Mouse interactions', async ({ page }) => {
  // Click
  await page.click('button');
  
  // Double click
  await page.dblclick('button');
  
  // Right click (context menu)
  await page.click('button', { button: 'right' });
  
  // Hover
  await page.hover('button');
  
  // Mouse movement
  await page.mouse.move(100, 200);
  await page.mouse.down();
  await page.mouse.move(200, 100); // Drag
  await page.mouse.up();
  
  // Drag and drop
  const source = page.locator('id=source');
  const target = page.locator('id=target');
  await source.dragTo(target);
});
```

### Q20: How do you test with different device viewports and mobile devices?
**Answer:**

```typescript
// Test with different viewports
test.describe('Responsive design', () => {
  test('Desktop view', async ({ page }) => {
    await page.setViewportSize({ width: 1920, height: 1080 });
    await page.goto('https://example.com');
    // Assert desktop layout
  });

  test('Tablet view', async ({ page }) => {
    await page.setViewportSize({ width: 768, height: 1024 });
    await page.goto('https://example.com');
    // Assert tablet layout
  });

  test('Mobile view', async ({ page }) => {
    await page.setViewportSize({ width: 375, height: 667 });
    await page.goto('https://example.com');
    // Assert mobile layout
  });
});

// Emulate specific devices
test('Test on iPhone 12', async ({ browser }) => {
  const iphone12 = playwright.devices['iPhone 12'];
  const context = await browser.newContext({
    ...iphone12,
    locale: 'en-US',
    geolocation: { latitude: 37.7749, longitude: -122.4194 },
    permissions: ['geolocation']
  });
  
  const page = await context.newPage();
  await page.goto('https://example.com');
});

// Available devices: iPhone, Pixel, Galaxy, etc.
const devices = playwright.devices;
console.log(Object.keys(devices)); // List all available devices
```

---

## Best Practices & Performance

### Q21: What are best practices for writing reliable tests?
**Answer:**

1. **Use Smart Locators**
```typescript
// ✓ Good - Stable and accessible
page.locator('role=button[name="Submit"]');
page.locator('[aria-label="Delete"]');

// ✗ Bad - Fragile, changes with UI
page.locator('button:nth-of-type(3)');
page.locator('div > div > div > button');
```

2. **Avoid Hard Waits**
```typescript
// ✗ Bad
await new Promise(resolve => setTimeout(resolve, 5000));

// ✓ Good
await page.waitForLoadState('networkidle');
await page.locator('.success').waitFor();
```

3. **Use Page Objects**
```typescript
// ✓ Good - Maintainable
const loginPage = new LoginPage(page);
await loginPage.login('email@test.com', 'password');

// ✗ Bad - Repetitive
await page.fill('input[name="email"]', 'email@test.com');
await page.fill('input[name="password"]', 'password');
// ... repeated in multiple tests
```

4. **One Assertion Per Test** (or related assertions)
```typescript
// ✓ Good
test('Login successful', async ({ page }) => {
  await loginPage.login('valid@test.com', 'password');
  expect(page.url()).toBe('/dashboard');
});

// ✗ Bad - Multiple unrelated assertions
test('All features work', async ({ page }) => {
  // ... login test
  // ... checkout test
  // ... profile test
});
```

5. **Use Fixtures for Setup/Teardown**
```typescript
// ✓ Good - Clear and reusable
const test = base.extend({
  authenticatedPage: async ({ page }, use) => {
    await page.goto('https://example.com/login');
    await page.fill('input[name="email"]', 'test@example.com');
    await page.fill('input[name="password"]', 'password');
    await page.click('button[type="submit"]');
    await use(page);
  }
});

test('Dashboard loads', async ({ authenticatedPage }) => {
  await authenticatedPage.goto('/dashboard');
  // Already authenticated
});
```

### Q22: How do you structure a Playwright project?
**Answer:**

```
project-root/
├── tests/
│   ├── auth.spec.ts
│   ├── checkout.spec.ts
│   └── settings.spec.ts
├── pages/
│   ├── loginPage.ts
│   ├── dashboardPage.ts
│   └── checkoutPage.ts
├── fixtures/
│   ├── auth.fixture.ts
│   └── database.fixture.ts
├── utils/
│   ├── testData.ts
│   ├── helpers.ts
│   └── constants.ts
├── config/
│   └── playwright.config.ts
├── package.json
└── tsconfig.json
```

**Example project structure files:**

```typescript
// config/playwright.config.ts
import { defineConfig, devices } from '@playwright/test';

export default defineConfig({
  testDir: './tests',
  fullyParallel: true,
  forbidOnly: !!process.env.CI,
  retries: process.env.CI ? 2 : 0,
  workers: process.env.CI ? 1 : undefined,
  reporter: 'html',
  use: {
    baseURL: 'https://example.com',
    trace: 'on-first-retry',
  },
  projects: [
    {
      name: 'chromium',
      use: { ...devices['Desktop Chrome'] },
    },
    {
      name: 'firefox',
      use: { ...devices['Desktop Firefox'] },
    },
  ],
});
```

### Q23: What is test data management and how should it be handled?
**Answer:**

```typescript
// utils/testData.ts
export const testUsers = {
  valid: {
    email: 'valid@example.com',
    password: 'ValidPassword123!'
  },
  invalid: {
    email: 'invalid@example.com',
    password: 'wrongpassword'
  },
  admin: {
    email: 'admin@example.com',
    password: 'AdminPassword123!'
  }
};

export const testProducts = [
  { id: 1, name: 'Laptop', price: 999.99 },
  { id: 2, name: 'Phone', price: 599.99 },
  { id: 3, name: 'Tablet', price: 399.99 }
];

// tests/auth.spec.ts
import { testUsers } from '../utils/testData';

test('Login with valid credentials', async ({ page }) => {
  const loginPage = new LoginPage(page);
  await loginPage.goto();
  await loginPage.login(testUsers.valid.email, testUsers.valid.password);
  expect(page.url()).toContain('/dashboard');
});

// Using fixtures for dynamic test data
test.extend<{ randomUser: TestUser }>({
  randomUser: async ({}, use) => {
    const userId = Math.random().toString(36).substring(7);
    const user = {
      email: `user${userId}@test.com`,
      password: 'TempPassword123!'
    };
    await use(user);
    // Cleanup if needed
  }
});
```

### Q24: How do you implement CI/CD with Playwright?
**Answer:**

```yaml
# .github/workflows/playwright.yml
name: Playwright Tests

on:
  push:
    branches: [ main, develop ]
  pull_request:
    branches: [ main ]

jobs:
  test:
    timeout-minutes: 60
    runs-on: ubuntu-latest
    strategy:
      matrix:
        node-version: [16, 18]
    
    steps:
      - name: Checkout code
        uses: actions/checkout@v3
      
      - name: Setup Node.js
        uses: actions/setup-node@v3
        with:
          node-version: ${{ matrix.node-version }}
      
      - name: Install dependencies
        run: npm ci
      
      - name: Install Playwright browsers
        run: npx playwright install --with-deps
      
      - name: Run tests
        run: npm run test:e2e
      
      - name: Upload test results
        if: always()
        uses: actions/upload-artifact@v3
        with:
          name: playwright-report
          path: playwright-report/
          retention-days: 30
```

### Q25: What are performance optimization techniques?
**Answer:**

```typescript
// 1. Parallel test execution
// playwright.config.ts
export default defineConfig({
  fullyParallel: true, // Run all tests in parallel
  workers: 4 // Use 4 workers
});

// 2. Sharding tests across machines
// Run on multiple machines in CI/CD
// npx playwright test --shard=1/3 (machine 1 of 3)

// 3. Disable animations and transitions
test('Test with animations disabled', async ({ page }) => {
  await page.addInitScript(() => {
    document.documentElement.style.scrollBehavior = 'auto';
  });
  
  await page.goto('https://example.com');
});

// 4. Use test.only for targeted testing
test.only('Critical test', async ({ page }) => {
  // Only this test runs
});

// 5. Reuse authentication across tests
const loginFixture = test.extend({
  authenticatedPage: async ({ page }, use) => {
    // Login once, reuse across tests
    await page.goto('/login');
    await page.fill('input[name="email"]', 'user@test.com');
    await page.fill('input[name="password"]', 'password');
    await page.click('button[type="submit"]');
    await use(page);
  }
});

// 6. Use baseURL to reduce URL typing
// playwright.config.ts
use: {
  baseURL: 'https://example.com',
  // In tests: await page.goto('/dashboard');
}
```

---

## Troubleshooting & Debugging

### Q26: How do you debug Playwright tests?
**Answer:**

```typescript
// 1. Using Playwright Inspector
// Run: npx playwright test --debug
test('Debug test', async ({ page }) => {
  await page.goto('https://example.com');
  // Debugging UI opens, can step through
});

// 2. Using console output
test('Test with logging', async ({ page }) => {
  console.log('Page URL:', page.url());
  
  page.on('console', msg => {
    console.log('Page console:', msg.text());
  });
  
  await page.goto('https://example.com');
});

// 3. Taking screenshots
test('Capture screenshot', async ({ page }) => {
  await page.goto('https://example.com');
  await page.screenshot({ path: 'screenshot.png' });
  // Full page
  await page.screenshot({ path: 'fullpage.png', fullPage: true });
});

// 4. Recording videos
// playwright.config.ts
use: {
  video: 'retain-on-failure', // Record only failed tests
  // or 'on' for all, 'off' for none
}

// 5. Trace recording (recommended)
// playwright.config.ts
use: {
  trace: 'on-first-retry',
}
// View: npx playwright show-trace trace.zip

// 6. Using debugger
test('Debug with VSCode', async ({ page }) => {
  debugger; // Set breakpoint
  await page.goto('https://example.com');
});
// Run: node --inspect-brk node_modules/.bin/playwright test

// 7. Step through in VSCode
// Set breakpoints and use F10 to step through
```

### Q27: What are common Playwright errors and how to fix them?
**Answer:**

| Error | Cause | Solution |
|-------|-------|----------|
| **Timeout Error** | Element not found within timeout | Use explicit waits, check selectors, increase timeout |
| **Navigation Error** | Page failed to load | Check URL, network, use `waitUntil` option |
| **Stale Element** | Element removed from DOM | Wait for element, use fresh locators |
| **Selector not found** | Incorrect selector or dynamic content | Verify selector, wait for element to load |
| **Not clickable** | Element obscured or not visible | Wait for element, scroll into view |
| **Cross-origin issue** | Cannot access cross-origin frame | Use `frameLocator`, not `page` |

```typescript
// Example fixes
test('Fix timeout errors', async ({ page }) => {
  // Before: Fails with timeout
  // await page.click('button.submit'); // Element not found
  
  // After: Wait for element
  await page.waitForSelector('button.submit', { visible: true });
  await page.click('button.submit');
});

test('Fix navigation errors', async ({ page }) => {
  // Before: Timeout
  // await page.goto('https://example.com');
  
  // After: Specify wait condition
  await page.goto('https://example.com', { 
    waitUntil: 'networkidle' 
  });
});

test('Fix cross-origin issues', async ({ page }) => {
  // Accessing iframe content
  const frameLocator = page.frameLocator('iframe[id="frame"]');
  const button = frameLocator.locator('button');
  await button.click();
});
```

### Q28: How do you handle dynamic content and AJAX requests?
**Answer:**

```typescript
// 1. Wait for elements added by JavaScript
test('Handle dynamic content', async ({ page }) => {
  await page.goto('https://example.com');
  
  // Wait for dynamically added element
  await page.waitForSelector('.dynamic-content', { timeout: 5000 });
  const content = await page.textContent('.dynamic-content');
  expect(content).toBeTruthy();
});

// 2. Wait for specific XHR/Fetch requests
test('Wait for AJAX response', async ({ page }) => {
  const responsePromise = page.waitForResponse(
    response => response.url().includes('/api/data') && response.status() === 200
  );
  
  await page.click('button'); // Triggers AJAX
  const response = await responsePromise;
  const data = await response.json();
  
  expect(data).toHaveProperty('id');
});

// 3. Wait for network idle
test('Wait for all requests', async ({ page }) => {
  await page.goto('https://example.com');
  await page.click('button'); // Triggers multiple AJAX calls
  
  // Wait until all network requests complete
  await page.waitForLoadState('networkidle');
  
  // Now assert on loaded content
  const result = await page.textContent('.result');
  expect(result).toBeTruthy();
});

// 4. Handle requests in parallel
test('Handle parallel requests', async ({ page }) => {
  const [response1, response2] = await Promise.all([
    page.waitForResponse(response => response.url().includes('/api/users')),
    page.waitForResponse(response => response.url().includes('/api/posts')),
    page.click('button') // Triggers both requests
  ]);
});
```

### Q29: How do you test error handling and edge cases?
**Answer:**

```typescript
// 1. Test error states
test('Handle API errors', async ({ page }) => {
  // Mock API error
  await page.route('**/api/data', route => {
    route.fulfill({
      status: 500,
      body: 'Internal Server Error'
    });
  });
  
  await page.goto('https://example.com');
  await page.click('button'); // Triggers failing API call
  
  // Assert error message appears
  const errorMessage = await page.textContent('.error');
  expect(errorMessage).toContain('Error loading data');
});

// 2. Test validation errors
test('Form validation errors', async ({ page }) => {
  const loginPage = new LoginPage(page);
  await loginPage.goto();
  await loginPage.login('', ''); // Empty fields
  
  const errors = await page.locator('[role="alert"]').allTextContents();
  expect(errors).toContain('Email is required');
  expect(errors).toContain('Password is required');
});

// 3. Test network failures
test('Handle network timeout', async ({ page }) => {
  // Abort slow requests
  await page.route('**/*', route => {
    if (route.request().url().includes('slow-api')) {
      route.abort();
    } else {
      route.continue();
    }
  });
  
  await page.goto('https://example.com');
  
  // Assert graceful handling
  const fallback = await page.textContent('.fallback-content');
  expect(fallback).toBeTruthy();
});

// 4. Test edge cases
test('Handle empty data', async ({ page }) => {
  await page.route('**/api/list', route => {
    route.fulfill({
      status: 200,
      body: JSON.stringify({ items: [] })
    });
  });
  
  await page.goto('https://example.com');
  const emptyMessage = await page.textContent('.empty-state');
  expect(emptyMessage).toContain('No items found');
});
```

### Q30: What are anti-patterns to avoid?
**Answer:**

```typescript
// ✗ ANTI-PATTERN 1: Hard waits
await new Promise(resolve => setTimeout(resolve, 5000));

// ✓ CORRECT: Use Playwright's built-in waits
await page.waitForLoadState('networkidle');

// ✗ ANTI-PATTERN 2: Weak selectors
page.locator('div').click(); // Selects first div, unclear intent

// ✓ CORRECT: Use specific, stable selectors
page.locator('role=button[name="Submit"]').click();

// ✗ ANTI-PATTERN 3: Testing implementation details
expect(element.classList.contains('active')).toBe(true);

// ✓ CORRECT: Test user-visible behavior
expect(page.locator('[aria-pressed="true"]')).toBeVisible();

// ✗ ANTI-PATTERN 4: Multiple unrelated assertions
test('User flow', async ({ page }) => {
  // Login
  // Browse products
  // Add to cart
  // Checkout
  // All in one test!
});

// ✓ CORRECT: Focused tests with clear purpose
test('Login with valid credentials', async ({ page }) => {
  // Just test login
});

test('Add product to cart', async ({ page }) => {
  // Just test add to cart
});

// ✗ ANTI-PATTERN 5: Not using fixtures
test.beforeEach(async ({ page }) => {
  await page.goto('https://example.com/login');
  // ... login
  // Repeated across multiple tests
});

// ✓ CORRECT: Use fixtures for reusability
const test = base.extend({
  authenticatedPage: async ({ page }, use) => {
    // Setup once
    await use(page);
  }
});

// ✗ ANTI-PATTERN 6: Hardcoded wait times
await page.waitForLoadState('networkidle');
await new Promise(r => setTimeout(r, 1000));

// ✓ CORRECT: Use appropriate wait conditions
await page.waitForLoadState('networkidle');
await page.locator('.content').waitFor({ state: 'visible' });

// ✗ ANTI-PATTERN 7: Ignoring test data
const email = 'random123@test.com';
const password = 'SomePassword123';

// ✓ CORRECT: Use centralized test data
const { email, password } = testUsers.valid;
```

---

## Summary

This comprehensive guide covers essential QA Automation concepts with Playwright in TypeScript/JavaScript. Key takeaways:

- **Master the fundamentals**: Understand testing types and automation frameworks
- **Learn Playwright**: Use modern selectors, locators, and waiting mechanisms
- **Write TypeScript**: Leverage type safety and modern JavaScript features
- **Follow best practices**: Use Page Object Model, fixtures, and stable selectors
- **Debug effectively**: Use Playwright Inspector, traces, and screenshots
- **Avoid anti-patterns**: Don't use hard waits, weak selectors, or unfocused tests

**Resources:**
- [Playwright Documentation](https://playwright.dev)
- [Playwright API Reference](https://playwright.dev/docs/api/class-playwright)
- [Testing Best Practices](https://playwright.dev/docs/best-practices)
