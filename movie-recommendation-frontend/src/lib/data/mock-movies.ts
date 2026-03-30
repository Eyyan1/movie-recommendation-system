export type MovieCardData = {
	id: number;
	title: string;
	genres: string[];
	releaseYear: string;
	rating: string;
	description: string;
	posterGradient: string;
};

export const featuredMovies: MovieCardData[] = [
	{
		id: 1,
		title: 'City of Shadows',
		genres: ['Thriller', 'Mystery'],
		releaseYear: '2024',
		rating: '4.7',
		description: 'A moody urban thriller about surveillance, memory, and disappearing witnesses.',
		posterGradient: 'from-amber-400 via-orange-500 to-rose-700'
	},
	{
		id: 2,
		title: 'Starlight Run',
		genres: ['Sci-Fi', 'Adventure'],
		releaseYear: '2023',
		rating: '4.5',
		description: 'A crew of smugglers crosses collapsing star lanes to deliver one impossible passenger.',
		posterGradient: 'from-sky-400 via-cyan-500 to-blue-800'
	},
	{
		id: 3,
		title: 'Velvet Kingdom',
		genres: ['Fantasy', 'Drama'],
		releaseYear: '2025',
		rating: '4.8',
		description: 'An heir returns to a fractured court where stories are currency and silence is power.',
		posterGradient: 'from-red-400 via-pink-500 to-fuchsia-800'
	}
];

export const ratingHistory = [
	{ title: 'Northern Echo', score: '5 / 5', updatedAt: '2 days ago' },
	{ title: 'Static Bloom', score: '4 / 5', updatedAt: '1 week ago' },
	{ title: 'Crimson Harbor', score: '5 / 5', updatedAt: '2 weeks ago' }
];

export const adminRows = [
	{ title: 'City of Shadows', releaseDate: '2024-10-11', source: 'TMDb import' },
	{ title: 'Velvet Kingdom', releaseDate: '2025-03-08', source: 'Manual + imported genres' },
	{ title: 'Quiet Voltage', releaseDate: '2023-07-19', source: 'TMDb import' }
];
