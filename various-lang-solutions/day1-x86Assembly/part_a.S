#include "part_a.h"

.globl part_a
.func part_a
part_a:
    # %rdi is char pointer
    
    # Set int rax/best = 0
    movq $0, %rax
    # Set int r9/total = 0
    movq $0, %r9
    # Set int r10/number = 0
    movq $0, %r10 
    # Set int r11/prevWas\n = 0
    movq $0, %r11

    jmp loopStart

loop:

    incq %rdi

loopStart:

    # Get char at RDI (char *)
    movq $0, %r8
    movb (%rdi), %r8b
    cmpq $0, %r8
    je end
    cmpq $10, %r8
    je newline
    # Prev was not new line
    movq $0, %r11
    # Multiply r10/number by 10
    imulq $10, %r10
    # Convert char to digit and add to number
    subq $48, %r8
    addq %r8, %r10

    jmp loop

newline:
    cmpq $1, %r11
    je section
    movq $1, %r11
    # Add number/r10 to total/r9
    addq %r10, %r9
    movq $0, %r10
    jmp loop

section:
    cmpq %rax, %r9
    jge greater
    jmp comparisonEnd

greater:
    movq %r9, %rax

comparisonEnd:
    movq $0, %r9
    jmp loop

end:
    ret
.endfunc
