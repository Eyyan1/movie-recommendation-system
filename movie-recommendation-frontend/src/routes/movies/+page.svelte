<script lang="ts">
	import MovieCard from '$lib/components/MovieCard.svelte';
	import SectionHeader from '$lib/components/SectionHeader.svelte';
	import { featuredMovies } from '$lib/data/mock-movies';
</script>

<svelte:head>
	<title>Movies</title>
</svelte:head>

<div class="space-y-8">
	<SectionHeader
		eyebrow="Browse"
		title="Movie catalog foundation"
		description="This page is shaped for future backend search and genre filtering. For now it demonstrates the intended layout, filter area, and responsive card system."
	/>

	<section class="rounded-[1.75rem] border border-white/10 bg-white/5 p-5">
		<div class="grid gap-4 lg:grid-cols-[2fr_1fr_auto]">
			<input
				class="rounded-2xl border border-white/10 bg-slate-950/80 px-4 py-3 text-sm text-white outline-none placeholder:text-slate-500"
				placeholder="Search titles like Batman or Dune"
			/>
			<select class="rounded-2xl border border-white/10 bg-slate-950/80 px-4 py-3 text-sm text-white outline-none">
				<option>All genres</option>
				<option>Action</option>
				<option>Thriller</option>
				<option>Sci-Fi</option>
				<option>Drama</option>
			</select>
			<button class="rounded-2xl bg-amber-300 px-5 py-3 text-sm font-semibold text-slate-950">Apply</button>
		</div>
	</section>

	<section class="grid gap-6 md:grid-cols-2 xl:grid-cols-3">
		{#each featuredMovies as movie}
			<MovieCard {movie} />
		{/each}
	</section>
</div>
