package com.unravel.demo;

import com.unravel.demo.concurrency.PriorityLogProcessor;
import com.unravel.demo.concurrency.Task;
import com.unravel.demo.db.DatabaseManager;
import com.unravel.demo.memory.MemoryManager;
import com.unravel.demo.session.SessionManager;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class DemoApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	void testSessionManager() {
		SessionManager sm = new SessionManager();
		String userId = "testUser";
		String sessionId = sm.login(userId);
		assertNotNull(sessionId);

		String retrieved = sm.getSessionDetails(userId);
		assertTrue(retrieved.contains(sessionId));

		String logoutMsg = sm.logout(userId);
		assertEquals("Logout successful.", logoutMsg);
	}

	@Test
	void testMemoryManager() {
		String sessionId = "testSession";
		assertDoesNotThrow(() -> MemoryManager.addSessionData(sessionId),
				"Adding session data should not throw.");
		assertDoesNotThrow(() -> MemoryManager.removeSessionData(sessionId),
				"Removing session data should not throw.");
	}

	@Test
	void testProducerConsumerPriority() throws InterruptedException {
		PriorityLogProcessor processor = new PriorityLogProcessor();

		processor.produce(new Task("LowPriorityTask", 5));
		processor.produce(new Task("HighPriorityTask", 1));

		Task firstConsumed = processor.consume();
		assertEquals("HighPriorityTask", firstConsumed.getName(),
				"Should consume the highest priority task first.");

		Task secondConsumed = processor.consume();
		assertEquals("LowPriorityTask", secondConsumed.getName(),
				"Should consume the lower priority task second.");
	}

	@Test
	void testDatabaseConnection() {
		assertDoesNotThrow(() -> {
			try (Connection conn = DatabaseManager.getConnection()) {
				assertFalse(conn.isClosed(), "Connection should be open.");
			}
		}, "Acquiring and releasing DB connection should not throw.");
	}

}
