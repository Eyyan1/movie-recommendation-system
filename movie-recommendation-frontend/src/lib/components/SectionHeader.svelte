<script lang="ts">
	let { eyebrow, title, description }: { eyebrow: string; title: string; description: string } = $props();
</script>

<div class="max-w-2xl">
	<p class="text-sm font-semibold uppercase tracking-[0.24em] text-sky-300">{eyebrow}</p>
	<h2 class="mt-2 font-display text-3xl text-white">{title}</h2>
	<p class="mt-3 text-base leading-7 text-slate-400">{description}</p>
</div>
