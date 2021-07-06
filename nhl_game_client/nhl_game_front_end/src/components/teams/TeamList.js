import React from 'react'
import Team from './Team'

const TeamList = ({teams}) => {

    if (teams.length === 0) {
        return (<p>Loading...</p>)
    }

    const teamsNodes = teams.map((team, index) => {
        return (
            <li key={index} className="component-item">
                <div className="component">
                    <Team team={Team} />
                </div>
            </li>
        )
    })

    return (
        <ul className="component-list">
            {teamsNodes}
        </ul>
    )
}

export default TeamList