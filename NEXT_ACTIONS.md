# Immediate Actions - High-Value Files

Based on AST analysis, here are the concrete next steps.

## Summary

- **Files Present:** 5/6 (83.3%)
- **Function parity:** 13/29 matched (target 16) — 44.8%
- **Class/type parity:** 4/6 matched (target 6) — 66.7%
- **Combined symbol parity:** 17/35 matched (target 22) — 48.6%
- **Average inline-code cosine:** 0.31 (function body across 4 matched files)
- **Average documentation cosine:** 0.76 (doc text across 4 matched files)
- **Cheat-zeroed Files:** 2
- **Critical Issues:** 5 files with <0.60 function similarity

## Priority 1: Fix Incomplete High-Dependency Files

No incomplete high-dependency files detected.

## Priority 2: Port Missing High-Value Files

Critical missing files (>10 dependencies):

No missing high-value files detected.

## Detailed Work Items

Every matched file is listed below with function and type symbol parity.

### 1. metadata

- **Target:** `includedir.Metadata [ZERO] [PROVENANCE-FALLBACK]`
- **Similarity:** 0.00
- **Dependents:** 1
- **Priority Score:** 1040510.0
- **Functions:** 0/4 matched (target 0)
- **Missing functions:** `new`, `accessed`, `created`, `modified`
- **Types:** 1/1 matched
- **Missing types:** _none_
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `src/metadata.rs` vs expected `metadata.rs`
- **Proposed provenance header:** `// port-lint: source metadata.rs` (current: `// port-lint: source src/metadata.rs`)
- **Lint issues:** 1

### 2. file

- **Target:** `includedir.File [PROVENANCE-FALLBACK]`
- **Similarity:** 0.19
- **Dependents:** 0
- **Priority Score:** 50808.1
- **Functions:** 2/7 matched (target 5)
- **Missing functions:** `new`, `path`, `contents`, `metadata`, `fmt`
- **Types:** 1/1 matched
- **Missing types:** _none_
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `src/file.rs` vs expected `file.rs`
- **Proposed provenance header:** `// port-lint: source file.rs` (current: `// port-lint: source src/file.rs`)
- **Lint issues:** 1

### 3. dir

- **Target:** `includedir.Dir [PROVENANCE-FALLBACK]`
- **Similarity:** 0.54
- **Dependents:** 0
- **Priority Score:** 31104.6
- **Functions:** 7/10 matched (target 7)
- **Missing functions:** `new`, `path`, `entries`
- **Types:** 1/1 matched
- **Missing types:** _none_
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `src/dir.rs` vs expected `dir.rs`
- **Proposed provenance header:** `// port-lint: source dir.rs` (current: `// port-lint: source src/dir.rs`)
- **Lint issues:** 1

### 4. lib

- **Target:** `includedir.Lib [STUB] [PROVENANCE-FALLBACK]`
- **Similarity:** 0.00
- **Dependents:** 0
- **Priority Score:** 10110.0
- **Functions:** 0/1 matched (target 0)
- **Missing functions:** `check_readme_examples`
- **Types:** 0/0 matched
- **Missing types:** _none_
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `src/lib.rs` vs expected `lib.rs`
- **Proposed provenance header:** `// port-lint: source lib.rs` (current: `// port-lint: source src/lib.rs`)
- **Lint issues:** 1

### 5. dir_entry

- **Target:** `includedir.DirEntry [PROVENANCE-FALLBACK]`
- **Similarity:** 0.53
- **Dependents:** 0
- **Priority Score:** 504.7
- **Functions:** 4/4 matched
- **Missing functions:** _none_
- **Types:** 1/1 matched (target 3)
- **Missing types:** _none_
- **Provenance warning:** port-lint provenance header matched only after fallback normalization: `src/dir_entry.rs` vs expected `dir_entry.rs`
- **Proposed provenance header:** `// port-lint: source dir_entry.rs` (current: `// port-lint: source src/dir_entry.rs`)
- **Lint issues:** 1

## Success Criteria

For each file to be considered "complete":
- **Similarity ≥ 0.85** (Excellent threshold)
- All public APIs ported
- All tests ported
- Documentation ported
- port-lint header present

## Next Commands

```bash
# Initialize task queue for systematic porting
cd tools/ast_distance
./ast_distance --init-tasks ../../tmp/include_dir/src rust ../../src/commonMain/kotlin/io/github/kotlinmania/includedir kotlin tasks.json ../../AGENTS.md

# Get next high-priority task
./ast_distance --assign tasks.json <agent-id>
```
