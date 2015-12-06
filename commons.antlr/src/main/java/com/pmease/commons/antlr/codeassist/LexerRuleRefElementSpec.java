package com.pmease.commons.antlr.codeassist;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.google.common.base.Optional;

public class LexerRuleRefElementSpec extends TokenElementSpec {

	private static final long serialVersionUID = 1L;

	private final String ruleName;
	
	private transient Optional<RuleSpec> rule;
	
	public LexerRuleRefElementSpec(CodeAssist codeAssist, String label, Multiplicity multiplicity, 
			int tokenType, String ruleName) {
		super(codeAssist, label, multiplicity, tokenType);

		this.ruleName = ruleName;
	}

	public String getRuleName() {
		return ruleName;
	}
	
	public RuleSpec getRule() {
		if (rule == null)
			rule = Optional.fromNullable(codeAssist.getRule(ruleName));
		return rule.orNull();
	}

	@Override
	public List<ElementSuggestion> doSuggestFirst(ParseTree parseTree, Node parent,  
			String matchWith, Set<String> checkedRules) {
		if (getRule() != null)
			return getRule().suggestFirst(parseTree, new Node(this, parent, null), matchWith, checkedRules);
		else
			return new ArrayList<>();
	}

	@Override
	public MandatoryLiteralScan scanPrefixedMandatoryLiterals(Set<String> checkedRules) {
		if (!checkedRules.contains(ruleName) && getRule() != null) { // to avoid infinite loop
			checkedRules.add(ruleName);
		
			List<AlternativeSpec> alternatives = getRule().getAlternatives();
			// nothing will be mandatory if we have multiple alternatives 
			if (alternatives.size() == 1) {
				List<String> mandatories = new ArrayList<>();
				for (ElementSpec elementSpec: alternatives.get(0).getElements()) {
					if (elementSpec.getMultiplicity() == Multiplicity.ZERO_OR_ONE 
							|| elementSpec.getMultiplicity() == Multiplicity.ZERO_OR_MORE) {
						// next input can either be current element, or other elements, so 
						// mandatory scan can be stopped
						return new MandatoryLiteralScan(mandatories, true);
					} else if (elementSpec.getMultiplicity() == Multiplicity.ONE_OR_MORE) {
						MandatoryLiteralScan scan = elementSpec.scanPrefixedMandatoryLiterals(new HashSet<>(checkedRules));
						mandatories.addAll(scan.getMandatoryLiterals());
						// next input can either be current element, or other elements, so 
						// mandatory scan can be stopped
						return new MandatoryLiteralScan(mandatories, true);
					} else {
						MandatoryLiteralScan scan = elementSpec.scanPrefixedMandatoryLiterals(new HashSet<>(checkedRules));
						mandatories.addAll(scan.getMandatoryLiterals());
						// if internal of the element tells use to stop, let's stop 
						if (scan.isStop())
							return new MandatoryLiteralScan(mandatories, true);
					}
				}
				return new MandatoryLiteralScan(mandatories, false);
			} else {
				return MandatoryLiteralScan.stop();
			}
		} else {
			return MandatoryLiteralScan.stop();
		}
	}

	@Override
	protected String asString() {
		return "lexer_rule_ref: " + ruleName;
	}
	
}
