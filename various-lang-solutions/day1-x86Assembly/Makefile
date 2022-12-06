CC      = gcc
CPPFLAGS  = -Wall -g -D_POSIX_SOURCE -D_DEFAULT_SOURCE -std=c17 -pedantic
.SUFFIXES:	.d .c .o

.PHONY:	default clean style

default:	day1

day1:	day1.o part_a.o part_b.o
part_a:	part_a.o
part_b:	part_b.o

.c.d:
	@set -e; rm -f $@; \
	$(CC) -MM $(CPPFLAGS) $< > $@.$$$$; \
	sed 's,\($*\)\.o[ :]*,\1.o $@ : ,g' < $@.$$$$ > $@; \
	rm -f $@.$$$$

.S.d:
	@set -e; rm -f $@; \
	$(CC) -MM $(CPPFLAGS) $< > $@.$$$$; \
	sed 's,\($*\)\.o[ :]*,\1.o $@ : ,g' < $@.$$$$ > $@; \
	rm -f $@.$$$$

sources = $(wildcard *.c)
assembly = $(wildcard *.S)

include $(sources:.c=.d)
include $(assembly:.S=.d)

clean:
	rm -f $(wildcard *.o)
	rm -f $(wildcard *.d)
	rm -f Day1

style:
	clang-format -style=file -i *.c *.h
