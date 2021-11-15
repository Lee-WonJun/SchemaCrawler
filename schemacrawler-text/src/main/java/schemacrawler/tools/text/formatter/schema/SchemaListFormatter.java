/*
========================================================================
SchemaCrawler
http://www.schemacrawler.com
Copyright (c) 2000-2021, Sualeh Fatehi <sualeh@hotmail.com>.
All rights reserved.
------------------------------------------------------------------------

SchemaCrawler is distributed in the hope that it will be useful, but
WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.

SchemaCrawler and the accompanying materials are made available under
the terms of the Eclipse Public License v1.0, GNU General Public License
v3 or GNU Lesser General Public License v3.

You may elect to redistribute this code under any of these licenses.

The Eclipse Public License is available at:
http://www.eclipse.org/legal/epl-v10.html

The GNU General Public License v3 and the GNU Lesser General Public
License v3 are available at:
http://www.gnu.org/licenses/

========================================================================
*/

package schemacrawler.tools.text.formatter.schema;

import static us.fatehi.utility.Utility.isBlank;

import schemacrawler.schema.ColumnDataType;
import schemacrawler.schema.CrawlInfo;
import schemacrawler.schema.DatabaseInfo;
import schemacrawler.schema.DatabaseObject;
import schemacrawler.schema.JdbcDriverInfo;
import schemacrawler.schema.Routine;
import schemacrawler.schema.Sequence;
import schemacrawler.schema.Synonym;
import schemacrawler.schema.Table;
import schemacrawler.tools.command.text.schema.options.SchemaTextDetailType;
import schemacrawler.tools.command.text.schema.options.SchemaTextOptions;
import schemacrawler.tools.options.OutputOptions;
import schemacrawler.tools.text.formatter.base.BaseFormatter;
import schemacrawler.tools.text.formatter.base.helper.TextFormattingHelper.DocumentHeaderType;
import schemacrawler.tools.traversal.SchemaTraversalHandler;
import us.fatehi.utility.html.Alignment;

/** Text formatting of schema. */
public final class SchemaListFormatter extends BaseFormatter<SchemaTextOptions>
    implements SchemaTraversalHandler {

  /**
   * Text formatting of schema.
   *
   * @param schemaTextDetailType Types for text formatting of schema
   * @param options Options for text formatting of schema
   * @param outputOptions Options for text formatting of schema
   * @param identifierQuoteString Quote character for identifier
   */
  public SchemaListFormatter(
      final SchemaTextDetailType schemaTextDetailType,
      final SchemaTextOptions options,
      final OutputOptions outputOptions,
      final String identifierQuoteString) {
    super(
        options,
        schemaTextDetailType == SchemaTextDetailType.details,
        outputOptions,
        identifierQuoteString);
  }

  /** {@inheritDoc} */
  @Override
  public void begin() {
    formattingHelper.writeDocumentStart();
  }

  /** {@inheritDoc} */
  @Override
  public void end() {
    formattingHelper.writeDocumentEnd();
    super.end();
  }

  /** {@inheritDoc} */
  @Override
  public void handle(final ColumnDataType columnDataType) {
    // No output required
  }

  @Override
  public void handle(final CrawlInfo crawlInfo) {
    if (crawlInfo == null) {
      return;
    }

    final String title = outputOptions.getTitle();
    if (!isBlank(title)) {
      formattingHelper.writeHeader(DocumentHeaderType.title, title);
    }

    if (options.isNoInfo()
        || options.isNoSchemaCrawlerInfo()
            && !options.isShowDatabaseInfo()
            && !options.isShowJdbcDriverInfo()) {
      return;
    }

    formattingHelper.writeHeader(DocumentHeaderType.subTitle, "System Information");

    if (!options.isNoSchemaCrawlerInfo()) {
      formattingHelper.writeObjectStart();
      formattingHelper.writeNameValueRow(
          "generated by", crawlInfo.getSchemaCrawlerVersion().toString(), Alignment.inherit);
      formattingHelper.writeNameValueRow(
          "generated on", crawlInfo.getCrawlTimestamp(), Alignment.inherit);
    }

    if (options.isShowDatabaseInfo()) {
      formattingHelper.writeNameValueRow(
          "database version", crawlInfo.getDatabaseVersion().toString(), Alignment.inherit);
    }
    if (options.isShowJdbcDriverInfo()) {
      formattingHelper.writeNameValueRow(
          "driver version", crawlInfo.getJdbcDriverVersion().toString(), Alignment.inherit);
    }

    formattingHelper.writeObjectEnd();
  }

  @Override
  public void handle(final DatabaseInfo dbInfo) {
    // No output required
  }

  @Override
  public void handle(final JdbcDriverInfo driverInfo) {
    // No output required
  }

  /** {@inheritDoc} */
  @Override
  public void handle(final Routine routine) {
    final String routineTypeDetail =
        String.format("%s, %s", routine.getRoutineType(), routine.getReturnType());
    final String routineName = quoteName(routine);
    final String routineType = "[" + routineTypeDetail + "]";

    formattingHelper.writeNameRow(routineName, routineType);
    printRemarks(routine);
  }

  /** {@inheritDoc} */
  @Override
  public void handle(final Sequence sequence) {
    final String sequenceName = quoteName(sequence);
    final String sequenceType = "[sequence]";

    formattingHelper.writeNameRow(sequenceName, sequenceType);
    printRemarks(sequence);
  }

  /** {@inheritDoc} */
  @Override
  public void handle(final Synonym synonym) {
    final String synonymName = quoteName(synonym);
    final String synonymType = "[synonym]";

    formattingHelper.writeNameRow(synonymName, synonymType);
    printRemarks(synonym);
  }

  @Override
  public void handle(final Table table) {
    final String tableName = quoteName(table);
    final String tableType = "[" + table.getTableType() + "]";

    formattingHelper.writeNameRow(tableName, tableType);
    printRemarks(table);
  }

  /** {@inheritDoc} */
  @Override
  public void handleColumnDataTypesEnd() {
    // No output required
  }

  /** {@inheritDoc} */
  @Override
  public void handleColumnDataTypesStart() {
    // No output required
  }

  @Override
  public void handleHeaderEnd() {
    // No output required
  }

  @Override
  public void handleHeaderStart() {
    // No output required
  }

  @Override
  public void handleInfoEnd() {
    // No output required
  }

  @Override
  public void handleInfoStart() {
    // No output required
  }

  /** {@inheritDoc} */
  @Override
  public void handleRoutinesEnd() {
    formattingHelper.writeObjectEnd();
  }

  /** {@inheritDoc} */
  @Override
  public void handleRoutinesStart() {
    formattingHelper.writeHeader(DocumentHeaderType.subTitle, "Routines");

    formattingHelper.writeObjectStart();
  }

  /** {@inheritDoc} */
  @Override
  public void handleSequencesEnd() {
    formattingHelper.writeObjectEnd();
  }

  /** {@inheritDoc} */
  @Override
  public void handleSequencesStart() {
    formattingHelper.writeHeader(DocumentHeaderType.subTitle, "Sequences");

    formattingHelper.writeObjectStart();
  }

  /** {@inheritDoc} */
  @Override
  public void handleSynonymsEnd() {
    formattingHelper.writeObjectEnd();
  }

  /** {@inheritDoc} */
  @Override
  public void handleSynonymsStart() {
    formattingHelper.writeHeader(DocumentHeaderType.subTitle, "Synonyms");

    formattingHelper.writeObjectStart();
  }

  /** {@inheritDoc} */
  @Override
  public void handleTablesEnd() {
    formattingHelper.writeObjectEnd();
  }

  /** {@inheritDoc} */
  @Override
  public void handleTablesStart() {
    formattingHelper.writeHeader(DocumentHeaderType.subTitle, "Tables");

    formattingHelper.writeObjectStart();
  }

  private void printRemarks(final DatabaseObject object) {
    if (object == null || !object.hasRemarks() || options.isHideRemarks()) {
      return;
    }
    formattingHelper.writeDescriptionRow(object.getRemarks());
  }
}
