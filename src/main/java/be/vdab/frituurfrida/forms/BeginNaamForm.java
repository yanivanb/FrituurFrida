package be.vdab.frituurfrida.forms;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

public record BeginNaamForm(@NotNull @NotEmpty String begin) {}
