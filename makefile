#
# define compiler and compiler flag variables
#


ODIR ?= build
ROOT_DIRECTORY ?=.
SOURCES := ${shell find ${ROOT_DIRECTORY} -type d -print}

J_FILES := $(foreach dir,$(SOURCES),  $(wildcard $(dir)/*.java) ) $(wildcard *.java)

JAVAFX_PATH=${shell pwd}/javafx-sdk-18.0.1/lib/
FX_MODS="javafx.graphics,javafx.controls"

JFLAGS = -g -d $(ODIR)
JC = javac

.SUFFIXES: .java .class


./build/%.class : %.java
	$(JC) --module-path "$(JAVAFX_PATH)" --add-modules $(FX_MODS) $(JFLAGS) $*.java 


CLASSES = $(J_FILES)


#
# the default make target entry
#
ORIGNINAL_DIR = ${shell pwd}
JAR_NAME ?= Maps.jar
MANIFEST_FILE ?= Manifest.mf

default: $(ODIR)/$(JAR_NAME)

classes: $(addprefix $(ODIR)/, $(CLASSES:.java=.class)) | $(ODIR)



$(ODIR)/$(JAR_NAME): classes
	@cd "$(ODIR)" &&  jar cfm $(JAR_NAME) $(MANIFEST_FILE) *.class *.png *.txt *.jpg

$(ODIR):
	mkdir -p $(ODIR)

#
# RM is a predefined macro in make (RM = rm -f)
#

clean:
	$(RM) build/*.class

debug:
	@cd "$(ODIR)" && java -jar -Xdebug -Xrunjdwp:transport=dt_socket,address=8000,server=y,suspend=y --module-path "$(JAVAFX_PATH)" --add-modules $(FX_MODS) $(JAR_NAME)

run: $(ODIR)/$(JAR_NAME)
	@cd "$(ODIR)" && java -jar --module-path "$(JAVAFX_PATH)" --add-modules $(FX_MODS) $(JAR_NAME)
