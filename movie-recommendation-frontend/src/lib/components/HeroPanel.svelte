<script lang="ts">
	let {
		eyebrow,
		title,
		description,
		primaryAction,
		secondaryAction
	}: {
		eyebrow: string;
		title: string;
		description: string;
		primaryAction: { label: string; href: string };
		secondaryAction: { label: string; href: string };
	} = $props();
</script>

<section class="relative overflow-hidden rounded-[2rem] border border-white/10 bg-[radial-gradient(circle_at_top_left,_rgba(251,191,36,0.18),_transparent_34%),radial-gradient(circle_at_top_right,_rgba(14,165,233,0.18),_transparent_28%),linear-gradient(135deg,_rgba(15,23,42,0.96),_rgba(30,41,59,0.82))] p-8 shadow-2xl shadow-slate-950/40 sm:p-12">
	<div class="absolute inset-0 bg-[linear-gradient(to_right,rgba(255,255,255,0.04)_1px,transparent_1px),linear-gradient(to_bottom,rgba(255,255,255,0.04)_1px,transparent_1px)] bg-[size:36px_36px] opacity-25"></div>
	<div class="relative max-w-3xl">
		<p class="mb-4 text-sm font-semibold uppercase tracking-[0.28em] text-amber-300">{eyebrow}</p>
		<h1 class="font-display text-4xl leading-tight text-white sm:text-5xl">{title}</h1>
		<p class="mt-5 max-w-2xl text-base leading-8 text-slate-300 sm:text-lg">{description}</p>
		<div class="mt-8 flex flex-wrap gap-3">
			<a
				href={primaryAction.href}
				class="rounded-full bg-amber-300 px-5 py-3 text-sm font-semibold text-slate-950 transition hover:bg-amber-200"
			>
				{primaryAction.label}
			</a>
			<a
				href={secondaryAction.href}
				class="rounded-full border border-white/15 px-5 py-3 text-sm font-semibold text-white transition hover:bg-white/8"
			>
				{secondaryAction.label}
			</a>
		</div>
	</div>
</section>
