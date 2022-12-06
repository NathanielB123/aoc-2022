#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/mman.h>
#include <unistd.h>

#include "part_a.h"
#include "part_b.h"

// Tested on Linux with WSL

#define PANIC(...)                            \
	{                                     \
		fprintf(stderr, __VA_ARGS__); \
		exit(EXIT_FAILURE);           \
	}

int int_max(int x, int y) { return x > y ? x : y; }

static size_t file_len(int fd) {
	FILE *file = fdopen(fd, "r");
	if (file == NULL) PANIC("Could not open file!\n");
	fseek(file, 0L, SEEK_END);
	return ftell(file);
}

int main(int argc, char **argv) {
	int fd = open("day1_input.txt", O_RDONLY, 0);

	if (fd == -1) PANIC("Could not open file!\n");

	size_t len = file_len(fd);

	char *data =
	    mmap(NULL, len, PROT_READ | PROT_WRITE, MAP_PRIVATE, fd, 0);
	if (data == NULL) PANIC("MMAP Failed!\n")
	printf("Part A: %li\n", part_a(data));
	printf("Part B: %li\n", part_b(data));
	/*
	int max = 0;
	int temp_total = 0;
	char *prog;

	for (char *token = strtok_r(data, "\n", &prog); prog < data + len;
	     token = strtok_r(NULL, "\n", &prog)) {
		if (strlen(token) == 0) {
			max = int_max(max, temp_total);
			temp_total = 0;
			printf("M: %i\n", max);
			continue;
		}
		temp_total += (int)strtol(token, NULL, 10);
	}
	printf("%i\n", max);*/
	if (munmap(data, len) == -1) PANIC("Failed to unmap!\n")
	close(fd);
	return EXIT_SUCCESS;
}
