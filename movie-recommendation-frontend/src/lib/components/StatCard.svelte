<script lang="ts">
	let { label, value, detail }: { label: string; value: string; detail: string } = $props();
</script>

<div class="rounded-[1.5rem] border border-white/10 bg-white/5 p-5">
	<p class="text-sm uppercase tracking-[0.18em] text-slate-400">{label}</p>
	<p class="mt-4 font-display text-3xl text-white">{value}</p>
	<p class="mt-2 text-sm leading-6 text-slate-400">{detail}</p>
</div>
