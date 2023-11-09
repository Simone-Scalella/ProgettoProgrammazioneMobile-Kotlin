# ProgettoProgrammazioneMobile-Kotlin

## Introduzione
L’applicazione sviluppata per questo progetto rappresenta una parte di un intero sistema, composto da server, database, applicazioni mobile e desktop; le applicazioni sviluppate servono per migliorare il lavoro dei dipendenti dell’azienda d’interesse, permette loro di svolgere con ulteriore semplicità operazioni quotidiane e permette di raccogliere le informazioni utili per il responsabile dell’azienda.

## Analisi degli obiettivi
Le applicazione hanno come target i tre tipi di dipendenti dell’azienda, che sono i dipendenti professionali, quelli generici e i cuochi. I dipendenti non devono effettuare nessuna registrazione, esistono già all’interno del sistema, quindi ogni utente deve effettuare il login, così accederà ad una home corrispondente alla tipologia di appartenenza. Nel caso in cui le credenziali inserite siano sbagliate l’utente verrà avvisato, inoltre, l’applicazione riconosce altri tipi di utenti non autorizzati ad usare l’applicazione, come ad esempio il responsabile che esiste nel sistema ma non può usare l’applicazione. Una volta effettuato il Login l’utente si trova nella sua home: 

1. Il cuoco può interagire con il magazzino, l’applicazione gli permette di visualizzare gli oggetti disponibili nel magazzino, all’interno di una lista. Il dipendente può compilare una form all’interno della quale inserisce le informazioni necessarie per poter utilizzare un determinato oggetto, in caso di errore nella compilazione l’utente deve essere avvisato, ad esempio se l’oggetto richiesto non esiste nel magazzino, inoltre, l’applicazione permette all’utente di visualizzare una lista contente tutte le proprie richieste fatte per utilizzare oggetti del magazzino. Il dipendente per tornare indietro deve necessariamente cliccare sul tasto Logout, in caso contrario viene avvisato della necessità di compiere quest’azione, e infine, può accedere ad una schermata dove vengono visualizzati tutti i suoi dati anagrafici.
   
2. Il dipendente generico possiede tutte le funzionalità del dipendente cuoco, in più questo dipendente può visualizzare le suddivisioni lavoro di un particolare dipendente all’interno di una lista, sempre dentro questa lista può anche eliminare una suddivisione lavoro di un dipendente dopo aver confermato di voler compiere tale azione, se il dipendente cercato non esiste l’utente viene avvisato dell’errore,riceve conferma se l’operazione di eliminazione è andata a buon fine. Infine il dipendente può compilare una form per inserire una suddivisione lavoro ad un dipendente professionale.

3. Il dipendente professionale possiede tutte le funzionalità del dipendente cuoco, in più questo dipendente può visualizzare in una lista tutte le sue prenotazioni per utilizzare una macchina pubblica,può eliminare una prenotazione e in una schermata specificando il nome del macchinario d’interesse, può visualizzare tutte le prenotazioni fatte dagli altri dipendenti. Quando un dipendente cancella una prenotazione il server genera dei messaggi per tutti i dipendenti professionali, questi messaggi servono per avvisarli. Nella home del dipendente professionale esiste un cronometro che ogni 10 secondi fa una richiesta al server, se la richiesta `e positiva lui cambia il colore del bottone ”messaggi”, in questo modo l’utente è avvisato dei cambiamenti, quando l’utente entra nella schermata dei messaggi, visualizza una lista con tutti i messaggi che gli sono stati assegnati, successivamente dopo aver visualizzato i messaggi il server li cancella. Inoltre il dipendente può compilare una form che gli permette di inserire una prenotazione per una macchina pubblica. Il dipendente può visualizzare una lista con tutte le suddivisioni lavoro a lui assegnate, può visualizzare in una lista tutti i trasferimenti di lavoro fatti e il loro stato ( confermato o non confermato ), all’interno di questa lista se l’elemento non è stato confermato è possibile eliminarlo dopo aver confermato l’intenzione di voler compiere tale azione. Possiede una schermata per inserire un nuovo trasferimento di lavoro.

## Approccio allo sviluppo

La realizzazione dell’applicazione parte con la raccolta dei requisiti, fondamentali per lo sviluppo, inoltre, bisognava sviluppare l’applicazione considerando che il server e il relativo database erano già stati implementati, quindi, si è partito con lo sviluppo delle singole schermate che avrebbero costituito l’applicazione, dividendo ogni componente in un sottoproblema.
Sono stati implementati i layout e le funzionalità richieste. A seguire le varie componenti sono state unificate secondo la logica dei requisiti.
Per la realizzazione dell’applicazione sono state utilizzate le documentazioni Kotlin e Android, più altre informazioni reperite su siti di terzi (ad esempio stackoverflow etc...), in questo modo è stato possibile superare le varie problematiche che si sono verificate in fase di sviluppo.

## Analisi dei requisiti

### Requisiti funzionali

Dopo aver dato una breve introduzione alle funzionalità della nostra applicazione, passiamo ora all’analisi funzionale nel dettaglio.
Partiamo descrivendo la schermata iniziale, quella di Login. A questo punto l’utente deve inserire le credenziali, cioè, username e password, i casi possono essere:
* L’utente esiste all’interno del server ma non rientra in una delle categorie specificate (’pro’,’gen’,’cuoco’) quindi si avvisa questa persona che non può proseguire nell’utilizzo dell’applicazione. 
* L’utente esiste all’interno del server e rientra in una delle categorie specificate, quindi, l’applicazione prosegue visualizzando la corrispondente home. 
* L’utente inserisce delle credenziali sbagliate quindi viene avvisato dell’errore. Se ci sono altri tipi di errori l’utente viene comunque avvisato.
* L’operazione di login ha avuto successo e otteniamo dal server una risposta, che contiene delle informazioni sull’utente e un token ( una stringa ) che sarà necessario per fare tutte le successive richieste al server. Passiamo dall’activity del Login a una delle activity che rappresenta la home del corrispondente dipendente e si potranno usare le varie funzionalità.

**Inserimento:** sono tutte le schermate che permettono all’utente di inserire dei dati, i quali tramite una POST vengono inviati al server, validati ed inseriti all’interno del DB; ci sono diverse schermate per l’inserimento, quelle per inserire una richiesta per l’utilizzo di un oggetto del magazzino, quella per inserire una suddivisione lavoro, quella per inserire la prenotazione di un macchinario e quella per inserire un trasferimento di lavoro, alcune di queste schermate sono accessibili da tutti, altre solo da specifici dipendenti. Se l’inserimento è andato a buon fine l’applicazione ritorna alla schermata iniziale e l’utente viene avvisato.

**Segnalazione Errori:** gli errori all’interno delle applicazioni vengono gestiti soprattutto con AlertDialog e a volte con dei Toast, la scelta degli AlertDialog è dovuta al fatto che il messaggio mostrato ha un titolo, un corpo e per essere tolto bisogna cliccare sullo schermo richiamando l’attenzione dell’utente. Quindi se durante la compilazione di una form vengono inseriti campi che non sono corretti come ad esempio un oggetto nel magazzino che non esiste, il server risponde alla richiesta con un codice di errore e l’applicazione lo gestisce. Per alcuni tipi di errori come ad esempio il codice fiscale, avendo una lunghezza fissa, se tale lunghezza non è rispettata non viene neanche fatta una richiesta al server, viene direttamente avvisato l’utente. I controlli vengono fatti anche sul Login e sui campi da compilare per visualizzare delle liste specifiche.

**Liste specifiche:** queste schermate contengono il risultato di particolari chiamate GET a cui vengono passati dei parametri inseriti dall’utente. Sono realizzate mediante l’utilizzo di una Recycler view, un esempio di tali schermate sono appunto quella che visualizza le suddivisioni lavoro del dip. generico e quella che visualizza le prenotazioni di un macchinario del dip. professionale.

**Liste generiche:** queste schermate invece contengono il risultato di chiamate GET fatte al server a cui non vengono passati parametri,sono realizzate mediante l’utilizzo di una Recycler view, come ad esempio la lista degli oggetti contenuti nel magazzino.

**Eliminazione:** E’ un messaggio AlertDialog che viene generato in seguito ad un LongClick da parte dell’utente sugli elementi di alcune liste, sia generiche,che specifiche. In seguito dopo aver confermato l’intenzione di voler eliminare l’elemento viene fatta una richiesta di tipo POST al server, in questo caso l’esito è necessariamente positivo in quanto nella post ci sono informazioni prese direttamente dal server, quindi viene avvisato l’utente e l’elemento cancellato viene segnato.

**Anagrafiche:** questa è una semplice schermata utile per visualizzare le informazioni personali del dipendente.

**LogOut:** il pulsante di Logout è l’unico che l’utente ha per tornare nella schermata di Login, la funzione del tasto Back è stata sovrascritta per ottenere questo risultato, infatti, se l’utente nella home preme il tasto Back viene sempre visualizzato un AlertDialog che lo avvisa della necessità di premere il tasto Logout se si intende effettuare la disconnessione. Questa scelta è stata fatta per evitare che l’utente si disconnetta premendo accidentalmente o ripetutamente il tasto Back, che invece, mantiene la sua funzione nelle altre schermate. Inoltre, nel tasto Logout viene chiamata un’ istruzione che termina l’activity e tutti i suoi processi.

**Messaggi:** per implementare una primitiva della notifica, cioè, per avvisare l’utente quando sono state modificate delle prenotazioni, è stato implementato un cronometro che, all’interno della home del dip. professionale effettua ogni 10 secondi una richiesta al server, se l’esito è positivo significa che ci sono dei messaggi per questo utente, altrimenti significa che l’utente non ha ricevuto dei messaggi, in base a questo risultato viene cambiato il colore del tasto messaggi, che diventa rosso per esito positivo, altrimenti blu.

### Requisiti non funzionali

I requisiti non funzionali sono:

**Server:** sviluppato con il framework laravel 8, usando il linguaggio PHP,
questo è lo strumento utilizzato per implementare il lato server del nostro sistema, quindi, per poter usare l’applicazione si rende necessario installare xamp per ottenere sulla propria macchina l’ambiente PHP necessario. Successivamente, bisogna copiare nella cartella HTDOCS il progetto del server.
Attualmente, il server gira in locale, su localhost, una volta spostato su un server dedicato bisogna cambiare un’istruzione tra le dipendenze necessarie per fare le richieste HTTP.

**Database:** sviluppato usando mysql e sql, il database è stato implementato da zero,abbiamo costruito tutte le tabelle, le loro relazioni e i vari vincoli.
Il database attualmente è popolato con informazioni di prova, cioè, le caratteristiche sono reali ma il contenuto è inventato, ad esempio i nomi o le date di
nascita sono inventate. Il server è l’unica applicazione che si interfaccia direttamente con il DB, tutte le richieste di lettura, scrittura, eliminazione e login
fatte per il DB vengono gestite prima dal server, così come anche le risposte che contengono le informazioni del DB.

## Architettura
### Classi Kotlin, Activity, Fragment

Per questa applicazione ogni POST ha una corrispondente Data Class così come anche ogni risposta del server ha una o più Data Class necessarie per fare il parsing. L’applicazione sfrutta activity e fragment secondo un’architettura modulare. Si è deciso di raggruppare in quattro macro categorie il progetto complessivo:

1. **welcome:** all’interno di questa cartella ci sono tutti tutte le Data Class necessarie per fare il parsing delle risposte del server e per fare le POST, la MainActivity che consiste nell’activity principale dell’app, quella da cui si fa il Login, con il suo viewModel, il validatore, un oggetto usato per effettuare dei controlli, e infine, è presente l’ ApiRequests che consiste nell’interfaccia all’interno della quale sono definite tutte le funzioni per fare le richieste GET o POST al server.

2. **homeCuoco:** all’interno di questa cartella c'è il fragment, che permette all’utente di usufruire delle varie funzionalità dell’app, e l’Activity principale che rappresenta la home del dipendente cuoco.

3. **homeGen:** all’interno di questa cartella c’`e il fragment che permette all’utente di usufruire delle varie funzionalit`a dell’app e l’Activity principale che rappresenta la home del dipendente generico, abbiamo una sotto-cartella per la suddivisione lavoro che al suo interno contiene due altre sotto-cartelle, una per l’inserimento e l’altra per la lista delle suddivisioni lavoro, nell’inserimento abbiamo la classe del fragment e le gi`a citate Data Class, nella classe per la lista abbiamo oltre alla Data Class e alla classe per il fragment, abbiamo l’adapter per la Recycler view della lista.

4. **homePro:** all’interno di questa cartella c'è il fragment che permette all’utente di usufruire delle varie funzionalità dell’app e l’Activity principale che rappresenta la home del dipendente professionale. Sono presenti delle sotto-cartelle, una per le anagrafiche, che contiene il fragment di visualizzazione delle anagrafiche. Le altre cartelle seguono una logica comune a quella della cartella suddivisione lavoro per il dipendente generico, infatti, abbiamo al loro interno altre sotto-cartelle per suddividere le liste dagli inserimenti. Ci sono le già citate Data Class, inoltre, sono presenti gli adapter per le Recycler view e le classi dei vari fragment.

## UI
Riporto di seguito il diagramma dei casi d’uso e le schermate che verranno visualizzate durante l’utilizzo dell’applicazione, considerando tutte le operazioni che possono essere svolte.

### Diagramma dei casi d’uso
