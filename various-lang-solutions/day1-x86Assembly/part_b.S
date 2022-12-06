#include "part_b.h"

.globl part_b
.func part_b
part_b:
    # %rdi is char pointer

    # Set int rax/best1 = 0
    movq $0, %rax
    # Set int rdx/best2 = 0
    movq $0, %rdx
    # Set int rdx/best3 = 0
    movq $0, %rcx

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
    cmpq %rdx, %rax
    jle raxSmaller

rdxSmaller:
    cmpq %rcx, %rdx
    jle rdxSmallest

rcxSmallest:
    cmpq %rcx, %r9
    jl comparisonEnd
    movq %r9, %rcx
    jmp comparisonEnd

rdxSmallest:
    cmpq %rdx, %r9
    jl comparisonEnd
    movq %r9, %rdx
    jmp comparisonEnd

raxSmaller:  
    cmpq %rax, %rcx
    jle rcxSmallest

raxSmallest:
    cmpq %rax, %r9
    jl comparisonEnd
    movq %r9, %rax
    jmp comparisonEnd

comparisonEnd:
    movq $0, %r9
    jmp loop

end:
    addq %rcx, %rax
    addq %rdx, %rax
    ret
.endfunc
