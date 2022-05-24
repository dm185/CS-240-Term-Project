#
# define compiler and compiler flag variables
#


ODIR ?= build
ROOT_DIRECTORY=.
SOURCES := ${shell find ${ROOT_DIRECTORY} -type d -print}

J_FILES := $(foreach dir,$(SOURCES),  $(wildcard $(dir)/*.java) ) $(wildcard *.java)

JAVAFX_PATH=/home/rendev/Documents/GitHub/CS-240-Term-Project/javafx-sdk-18.0.1/lib/
#/home/rendev/Desktop/Java/Proj/lib
FX_MODS="javafx.graphics,javafx.controls"
#javafx.application.Application,javafx.stage.Stage,javafx.scene.Scene,javafx.scene.layout.BorderPane

JFLAGS = -g -d $(ODIR)
JC = javac


#
# Clear any default targets for building .class files from .java files; we 
# will provide our own target entry to do this in this makefile.
# make has a set of default targets for different suffixes (like .c.o) 
# Currently, clearing the default for .java.class is not necessary since 
# make does not have a definition for this target, but later versions of 
# make may, so it doesn't hurt to make sure that we clear any default 
# definitions for these
#

.SUFFIXES: .java .class


#
# Here is our target entry for creating .class files from .java files 
# This is a target entry that uses the suffix rule syntax:
#	DSTS:
#		rule
#  'TS' is the suffix of the target file, 'DS' is the suffix of the dependency 
#  file, and 'rule'  is the rule for building a target	
# '$*' is a built-in macro that gets the basename of the current target 
# Remember that there must be a < tab > before the command line ('rule') 
#

./build/%.class : %.java
	$(JC) --module-path "$(JAVAFX_PATH)" --add-modules $(FX_MODS) $(JFLAGS) $*.java 


#
# CLASSES is a macro consisting of 4 words (one for each java source file)
#

CLASSES = $(J_FILES)


#
# the default make target entry
#

default: classes


#
# This target entry uses Suffix Replacement within a macro: 
# $(name:string1=string2)
# 	In the words in the macro named 'name' replace 'string1' with 'string2'
# Below we are replacing the suffix .java of all words in the macro CLASSES 
# with the .class suffix
#

classes: $(addprefix $(ODIR)/, $(CLASSES:.java=.class)) | $(ODIR)

$(ODIR):
	mkdir -p $(ODIR)

#
# RM is a predefined macro in make (RM = rm -f)
#

clean:
	$(RM) build/*.class

debug:
	java -Xdebug -Xrunjdwp:transport=dt_socket,address=8000,server=y,suspend=y --module-path "$(JAVAFX_PATH)" --add-modules $(FX_MODS) Main

run:
	@cd build && java --module-path "$(JAVAFX_PATH)" --add-modules $(FX_MODS) Main
	@cd ..
