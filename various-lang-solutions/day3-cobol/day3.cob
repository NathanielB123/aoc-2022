       IDENTIFICATION DIVISION.
       PROGRAM-ID. HELLO.
      
       ENVIRONMENT DIVISION.
         INPUT-OUTPUT SECTION.
         FILE-CONTROL.
           SELECT INPUT-FILE-NAME ASSIGN TO 'day3input.txt'
           ORGANIZATION IS LINE SEQUENTIAL.            
      
       DATA DIVISION.
         FILE SECTION.
         FD INPUT-FILE-NAME
           RECORD VARYING 0 to 100 DEPENDING ON WS-LINE-LEN.
         01 STUDENT-FILE.
           05 FILE-LINE PIC A(100).
      
         WORKING-STORAGE SECTION.
         01 WS-LINE PIC A(100).
         01 WS-EOF PIC A(1). 
         01 WS-SND-HALF PIC A(50).
         01 WS-I PIC S9(9) VALUE 0.
         01 WS-FOUND-CHR PIC A(1).
         01 WS-FOUND-CNT PIC 9 VALUE 0.
         01 WS-TMP PIC S9(9) VALUE 0.
         01 WS-TOTAL PIC S9(9) VALUE 0.
         01 WS-FOUND_CHR_ORD PIC S9(9) VALUE 0.
         01 WS-LINE-LEN PIC S9(9) VALUE 0.
      
       PROCEDURE DIVISION.
           OPEN INPUT INPUT-FILE-NAME.
              PERFORM UNTIL WS-EOF='Y'
              READ INPUT-FILE-NAME NEXT RECORD INTO WS-LINE
                 AT END MOVE 'Y' TO WS-EOF
                 NOT AT END
                 MOVE WS-LINE(WS-LINE-LEN / 2 + 1:) TO WS-SND-HALF
                 PERFORM VARYING WS-I FROM 1 BY 1 UNTIL 
                    WS-I = WS-LINE-LEN / 2 + 1
                    MOVE 0 to WS-FOUND-CNT
                    INSPECT WS-SND-HALF TALLYING WS-FOUND-CNT FOR ALL 
                       WS-LINE (WS-I:1)
                    IF WS-FOUND-CNT > 0 THEN
                       MOVE WS-LINE (WS-I:1) TO WS-FOUND-CHR
                    END-IF 
                 END-PERFORM
                 SET WS-FOUND_CHR_ORD TO FUNCTION ORD(WS-FOUND-CHR)
                 IF WS-FOUND_CHR_ORD >= FUNCTION ORD('a')
                    SUBTRACT 97 FROM WS-FOUND_CHR_ORD 
                       GIVING WS-TMP
                 ELSE
                    SUBTRACT 39 FROM WS-FOUND_CHR_ORD 
                       GIVING WS-TMP 
                 END-IF 
                 ADD WS-TMP TO WS-TOTAL
              END-READ
              END-PERFORM.
              DISPLAY "PART A: "
              DISPLAY WS-TOTAL
           CLOSE INPUT-FILE-NAME.
           STOP RUN.
