<script lang="ts">
	import SectionHeader from '$lib/components/SectionHeader.svelte';
	import { adminRows } from '$lib/data/mock-movies';
</script>

<svelte:head>
	<title>Admin Movies</title>
</svelte:head>

<div class="space-y-8">
	<SectionHeader
		eyebrow="Admin"
		title="Movie management shell"
		description="This route is intentionally frontend-only for now. It sets up the structure for admin CRUD screens that will later consume backend data and forms."
	/>

	<div class="flex justify-end">
		<button class="rounded-2xl bg-amber-300 px-5 py-3 text-sm font-semibold text-slate-950">Add movie</button>
	</div>

	<section class="overflow-hidden rounded-[1.75rem] border border-white/10 bg-white/5">
		<div class="grid grid-cols-[1.6fr_1fr_1fr] gap-4 border-b border-white/10 px-6 py-4 text-xs font-semibold uppercase tracking-[0.22em] text-slate-400">
			<span>Movie</span>
			<span>Release date</span>
			<span>Source</span>
		</div>
		{#each adminRows as row}
			<div class="grid grid-cols-[1.6fr_1fr_1fr] gap-4 border-b border-white/5 px-6 py-5 text-sm text-slate-200 last:border-b-0">
				<span class="font-medium text-white">{row.title}</span>
				<span>{row.releaseDate}</span>
				<span class="text-slate-400">{row.source}</span>
			</div>
		{/each}
	</section>
</div>
