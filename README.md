1.
Kas nutinka su einamąja transakcija, kai gaunamas OptimisticLockException?
	Rollbackinami pakeitimai, t.y. nera siunciami i DB

Kas nutinka su einamuoju EntityManager, kai gaunamas OptimisticLockException?
	Jis nera uzdaromas, reikia uzdaryti manually.

Kaip išsaugoti esybę į DB po OptimisticLockException?
	1 Pagauti exceptiona
	2 Rollback
	3 Close EntityManager
	4 Open new EntityManager
	5 Persist

2
Ar gali asinchroninis komponentas įsijungti į kvietėjo pradėtą transakciją?
	Ne:
	Asynchronous method does not inherit the caller's transaction context. Instead, it executes in a new transaction or with no transaction

Ar gali asinchroninis komponentas naudoti @RequestScoped EntityManager?
	Ne:
	Asynchronous operations typically run in a separate thread from the one that handles the HTTP request. Since @RequestScoped beans are bound to a specific HTTP request and consequently to the thread handling that request, they are not directly accessible from threads initiated by asynchronous components
